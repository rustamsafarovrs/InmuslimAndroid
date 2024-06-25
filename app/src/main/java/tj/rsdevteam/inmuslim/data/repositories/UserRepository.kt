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
        emit(Resource.InProgress())
        val result = api.registerUser(body)
        if (result.isSuccess && result.getOrNull()?.result == 0) {
            preferences.saveUserId(result.getOrThrow().id)
            emit(Resource.Success(result.getOrThrow()))
        } else {
            emit(errorHandler.getError(result))
        }
    }

    fun updateMessagingId(): Flow<Resource<UpdateMessagingIdResponse>> = flow {
        emit(Resource.InProgress())
        var msgid = ""
        withContext(Dispatchers.IO) {
            FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
                msgid = token
            }
        }
        val result = api.updateMessagingId(UpdateMessagingIdBody(id = preferences.getUserId(), msgid = msgid))
        if (result.isSuccess && result.getOrNull()?.result == 0) {
            preferences.saveFirebaseToken(msgid)
            emit(Resource.Success(result.getOrThrow()))
        } else {
            emit(errorHandler.getError(result))
        }
    }

    fun needRegister(): Boolean {
        return preferences.getUserId() == -1L
    }

    fun getUserId(): Long {
        return preferences.getUserId()
    }

    fun getFirebaseToken(): String {
        return preferences.getFirebaseToken()
    }
}
