package tj.rsdevteam.inmuslim.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tj.rsdevteam.inmuslim.data.models.api.GetRegionsDTO
import tj.rsdevteam.inmuslim.data.models.api.GetTimingBodyDTO
import tj.rsdevteam.inmuslim.data.models.api.GetTimingDTO
import tj.rsdevteam.inmuslim.data.models.api.RegisterUserBodyDTO
import tj.rsdevteam.inmuslim.data.models.api.RegisterUserDTO
import tj.rsdevteam.inmuslim.data.models.api.UpdateMessagingIdBodyDTO
import tj.rsdevteam.inmuslim.data.models.api.UpdateMessagingIdDTO

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

interface Api {

    @GET("GetRegions")
    suspend fun getRegions(): Result<GetRegionsDTO>

    @POST("GetTiming")
    suspend fun getTiming(@Body body: GetTimingBodyDTO): Result<GetTimingDTO>

    @POST("RegisterUser")
    suspend fun registerUser(@Body body: RegisterUserBodyDTO): Result<RegisterUserDTO>

    @POST("UpdateMessagingId")
    suspend fun updateMessagingId(@Body body: UpdateMessagingIdBodyDTO): Result<UpdateMessagingIdDTO>
}
