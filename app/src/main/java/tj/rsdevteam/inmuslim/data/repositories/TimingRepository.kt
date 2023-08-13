package tj.rsdevteam.inmuslim.data.repositories

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.rsdevteam.inmuslim.data.api.Api
import tj.rsdevteam.inmuslim.data.models.network.GetTimingBody
import tj.rsdevteam.inmuslim.data.models.network.GetTimingResponse
import tj.rsdevteam.inmuslim.data.models.network.Resource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@Singleton
class TimingRepository
@Inject constructor(
    private val api: Api,
    private val errorHandler: ErrorHandler
) {

    fun getTiming(body: GetTimingBody): Flow<Resource<GetTimingResponse>> = flow {
        try {
            emit(Resource.loading())
            val response = api.getTiming(body)
            delay(1000)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()!!))
            } else {
                emit(
                    errorHandler.getError(
                        response.code(),
                        response.errorBody(),
                        response.body()
                    )
                )
            }
        } catch (e: Exception) {
            emit(errorHandler.getError(e))
        }
    }
}