package tj.rsdevteam.inmuslim.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tj.rsdevteam.inmuslim.data.models.network.GetRegionsResponse
import tj.rsdevteam.inmuslim.data.models.network.GetTimingBody
import tj.rsdevteam.inmuslim.data.models.network.GetTimingResponse

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

interface Api {

    @GET("GetRegions")
    suspend fun getRegions(): Response<GetRegionsResponse>

    @POST("GetTiming")
    suspend fun getTiming(@Body body: GetTimingBody): Response<GetTimingResponse>
}