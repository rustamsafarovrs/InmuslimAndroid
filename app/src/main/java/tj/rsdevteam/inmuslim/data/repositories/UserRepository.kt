package tj.rsdevteam.inmuslim.data.repositories

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import tj.rsdevteam.inmuslim.data.api.Api
import tj.rsdevteam.inmuslim.data.models.Message
import tj.rsdevteam.inmuslim.data.models.Resource
import tj.rsdevteam.inmuslim.data.models.User
import tj.rsdevteam.inmuslim.data.models.api.RegisterUserBodyDTO
import tj.rsdevteam.inmuslim.data.models.api.UpdateMessagingIdBodyDTO
import tj.rsdevteam.inmuslim.data.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rustam Safarov on 15/08/23.
 * github.com/rustamsafarovrs
 */

@Singleton
class UserRepository
@Inject constructor(
    private val api: Api,
    private val preferences: Preferences,
    private val errorHandler: ErrorHandler
) {

    fun registerUser(name: String): Flow<Resource<User>> = flow {
        emit(Resource.InProgress())
        val result = api.registerUser(RegisterUserBodyDTO(name = name))
        if (result.isSuccess && result.getOrNull()?.result == 0) {
            preferences.saveUserId(result.getOrThrow().id)
            emit(Resource.Success(result.getOrThrow().toUser()))
        } else {
            emit(errorHandler.getError(result))
        }
    }

    fun updateMessagingId(): Flow<Resource<Message>> = flow {
        emit(Resource.InProgress())
        var msgid = ""
        withContext(Dispatchers.IO) {
            FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
                msgid = token
            }
        }
        val result = api.updateMessagingId(UpdateMessagingIdBodyDTO(id = preferences.getUserId(), msgid = msgid))
        if (result.isSuccess && result.getOrNull()?.result == 0) {
            preferences.saveFirebaseToken(msgid)
            emit(Resource.Success(result.getOrThrow().toMessage()))
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
