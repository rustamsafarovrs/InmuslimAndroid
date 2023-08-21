package tj.rsdevteam.inmuslim.data.repositories

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import tj.rsdevteam.inmuslim.data.api.Api
import tj.rsdevteam.inmuslim.data.models.network.RegisterUserBody
import tj.rsdevteam.inmuslim.data.models.network.RegisterUserResponse
import tj.rsdevteam.inmuslim.data.models.network.Resource
import tj.rsdevteam.inmuslim.data.models.network.UpdateMessagingIdBody
import tj.rsdevteam.inmuslim.data.models.network.UpdateMessagingIdResponse
import tj.rsdevteam.inmuslim.data.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rustam Safarov on 15/08/23.
 * github.com/rustamsafarovrs
 */

@Singleton
@Suppress("TooGenericExceptionCaught")
class UserRepository
@Inject constructor(
    private val api: Api,
    private val preferences: Preferences,
    private val errorHandler: ErrorHandler
) {

    fun registerUser(body: RegisterUserBody): Flow<Resource<RegisterUserResponse>> = flow {
        try {
            emit(Resource.loading())
            val response = api.registerUser(body)
            if (response.isSuccessful && response.body()?.result == 0) {
                preferences.saveUserId(response.body()!!.id)
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

    fun updateMessagingId(): Flow<Resource<UpdateMessagingIdResponse>> = flow {
        try {
            emit(Resource.loading())
            var msgid = ""
            withContext(Dispatchers.IO) {
                FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
                    msgid = token
                }
            }
            val response = api.updateMessagingId(
                UpdateMessagingIdBody(
                    id = preferences.getUserId(),
                    msgid = msgid
                )
            )
            if (response.isSuccessful && response.body()?.result == 0) {
                preferences.saveFirebaseToken(msgid)
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

    fun needRegister(): Boolean {
        return preferences.getUserId() == -1L
    }

    fun getUserId() = preferences.getUserId()

    fun getFirebaseToken() = preferences.getFirebaseToken()
}
