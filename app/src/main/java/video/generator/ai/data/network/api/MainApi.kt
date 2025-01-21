package video.generator.ai.data.network.api

import video.generator.ai.data.network.api.model.generateResponse.GenerateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import video.generator.ai.data.network.api.model.jobResponse.JobResponse
import video.generator.ai.data.network.api.model.request.GenerateRequest


interface MainApi {

    @Headers(
        "Accept: application/json",
        "Authorization: Bearer 2b3b3ddb-d213-4a88-8f39-d800d6002118")
    @POST("generate")
    fun generateVideo(
        @Body generateRequest: GenerateRequest
    ) : Call<GenerateResponse>

    @Headers(
        "Accept: application/json",
        "Authorization: Bearer 2b3b3ddb-d213-4a88-8f39-d800d6002118")
    @GET("jobs/{id}")
    fun getJob(
        @Path("id") id: String
    ) : Call<JobResponse>
}