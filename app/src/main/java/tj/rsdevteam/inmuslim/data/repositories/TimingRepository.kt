package tj.rsdevteam.inmuslim.data.repositories

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.rsdevteam.inmuslim.data.api.Api
import tj.rsdevteam.inmuslim.data.constants.Constants
import tj.rsdevteam.inmuslim.data.models.network.GetTimingBody
import tj.rsdevteam.inmuslim.data.models.network.GetTimingResponse
import tj.rsdevteam.inmuslim.data.models.network.Resource
import tj.rsdevteam.inmuslim.data.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@Singleton
@Suppress("TooGenericExceptionCaught")
class TimingRepository
@Inject constructor(
    private val api: Api,
    private val preferences: Preferences,
    private val errorHandler: ErrorHandler
) {

    fun getTiming(): Flow<Resource<GetTimingResponse>> = flow {
        try {
            emit(Resource.loading())
            val response = api.getTiming(GetTimingBody(regionId = preferences.getRegionId()))
            delay(Constants.MIDDLE_DELAY)
            if (response.isSuccessful && response.body()?.result == 0) {
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
