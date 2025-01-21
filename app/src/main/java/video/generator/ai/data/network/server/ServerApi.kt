package video.generator.ai.data.network.server

import retrofit2.Call
import retrofit2.http.GET

interface ServerApi {

    @GET("api_x.php?id=soraKey")
    fun getStatus() : Call<Status>
}