package video.generator.ai.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import video.generator.ai.data.network.api.model.generateResponse.GenerateResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import video.generator.ai.data.local.GenerationDao
import video.generator.ai.data.local.model.Generation
import video.generator.ai.data.network.api.MainApi
import video.generator.ai.data.network.api.model.jobResponse.JobResponse
import video.generator.ai.data.network.api.model.request.GenerateRequest
import video.generator.ai.data.network.server.ServerApi
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val mainApi: MainApi,
    private val generationDao: GenerationDao,
    private val serverApi: ServerApi
) : ViewModel() {

    val videoLink = MutableStateFlow("")
    val videoHeader = MutableStateFlow("")
    val date = MutableStateFlow("")
    val isSuccess = MutableStateFlow(false)
    val generated = MutableStateFlow(false)
    val status = MutableStateFlow("")

    val _lastGeneration = MutableStateFlow<Generation>(Generation(prompt = "", videoLink = "", posterLink = "", date = ""))

    init {
        getStatus()
    }

    fun getStatus(){
        val call = serverApi.getStatus()
        call.enqueue(object : Callback<video.generator.ai.data.network.server.Status> {
            override fun onResponse(
                call: Call<video.generator.ai.data.network.server.Status?>,
                response: Response<video.generator.ai.data.network.server.Status?>
            ) {
                val responseBody = response.body()
                status.value = responseBody?.result!!
                Log.d("STATUS", status.value)
            }

            override fun onFailure(
                call: Call<video.generator.ai.data.network.server.Status?>,
                t: Throwable
            ) {
                Log.d("FAILURE", t.message.toString())
                status.value = "error"
            }
        })
    }

    fun addGeneration(generation: Generation){
        viewModelScope.launch(Dispatchers.IO){
            generationDao.insert(generation)
        }
    }

    fun generateVideo(
        generateRequest: GenerateRequest,
        context: Context
    ) {
        val call = mainApi.generateVideo(generateRequest)
        videoHeader.value = generateRequest.promptText!!
        call.enqueue(object : Callback<GenerateResponse> {
            override fun onResponse(
                call: Call<GenerateResponse?>,
                response: Response<GenerateResponse?>
            ) {
                val responseBody = response.body()
                if (responseBody != null){
                    isSuccess.value = true
                    Log.d("RESPONSE", responseBody.toString())
                    val jobId = responseBody?.job?.id!!
                    getVideoLink(jobId, context)
                }else{
                    Toast.makeText(context, "Generation Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<GenerateResponse?>,
                t: Throwable
            ) {
                Log.d("FAILURE", t.message.toString())
            }
        })
    }

    fun getVideoLink(
        jobId: String,
        context: Context
    ) {
        viewModelScope.launch(Dispatchers.IO){
            while (generated.value == false){
                delay(10000)
                val call = mainApi.getJob(jobId)
                call.enqueue(object : Callback<JobResponse> {
                    override fun onResponse(call: Call<JobResponse?>, response: Response<JobResponse?>) {
                        val responseBody = response.body()
                        if (responseBody != null){
                            Log.d("RESPONSE", responseBody.toString())
                            val status = responseBody.videos?.get(0)?.status!!
                            if (status == "finished"){
                                videoLink.value = responseBody.videos.get(0)?.resultUrl!!
                                date.value = responseBody.job?.createdAt?.substring(0, 10)!!
                                _lastGeneration.value = Generation(
                                    prompt = responseBody?.job?.promptText!!,
                                    videoLink = responseBody?.videos?.get(0)?.resultUrl!!,
                                    posterLink = responseBody?.videos?.get(0)?.videoPoster!!,
                                    date = responseBody?.job?.createdAt?.substring(0, 10)!!)
                                generated.value = true
                            }
                        }else{
                            Toast.makeText(context, "Generation Error", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<JobResponse?>,
                        t: Throwable
                    ) {
                        Log.d("FAILURE", t.message.toString())
                    }
                })
            }
        }
    }
}