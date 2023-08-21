package tj.rsdevteam.inmuslim.data.repositories

import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.HttpException
import tj.rsdevteam.inmuslim.data.constants.Constants
import tj.rsdevteam.inmuslim.data.models.network.ApiError
import tj.rsdevteam.inmuslim.data.models.network.Resource
import java.io.IOException

/**
 * Created by Rustam Safarov on 8/13/23.
 * github.com/rustamsafarovrs
 */

@Suppress("TooGenericExceptionCaught")
class ErrorHandler constructor(private val moshi: Moshi) {

    fun <T> getError(e: Throwable): Resource<T> {
        val exception = handleException(e)
        return getErrorResource(exception)
    }

    fun <T> getError(httpStatusCode: Int, responseBody: ResponseBody?): Resource<T> {
        val exception = handleHttpException(httpStatusCode, responseBody?.string())
        return getErrorResource(exception)
    }

    fun <T> getError(httpStatusCode: Int, errorBody: ResponseBody?, body: Any?): Resource<T> {
        if (errorBody != null) {
            val exception = handleHttpException(httpStatusCode, errorBody.string())
            return getErrorResource(exception)
        } else {
            val tmp = moshi.adapter(Any::class.java)
            val str: String = tmp.toJson(body)
            val exception = handleHttpException(httpStatusCode, str)
            return getErrorResource(exception)
        }
    }

    private fun handleException(e: Throwable): Exception =
        when (e) {
            is HttpException -> {
                handleHttpException(e.code(), e.response()?.errorBody()?.string())
            }

            is IOException -> {
                ConnectionTimeoutException()
            }

            else -> {
                e.printStackTrace()
                UnknownException(e.message.toString())
            }
        }

    private fun handleHttpException(code: Int, errorBody: String?): Exception =
        try {
            val error: ApiError = moshi.adapter(ApiError::class.java).fromJson(errorBody!!)!!
            if (code == Constants.UNAUTHORIZED) {
                SessionException()
            } else {
                ApiException(error)
            }
        } catch (e: Exception) {
            // Logger.exception(e)
            UnknownException(e.message.toString())
        }

    private fun <T> getErrorResource(e: Exception): Resource<T> {
        var message: String? = null

        when (e) {
            is ApiException -> {
                message = e.apiError.msg
            }

            is ConnectionTimeoutException -> {
                message = "No internet connection"
            }

            is UnknownException -> {
                message = "Something went wrong..."
            }

            is SessionException -> {
                message = e.message.toString()
            }
        }
        return Resource.error(msg = message, data = null, exception = e)

    }
}

class SessionException : Exception("Unauthorized")
class UnknownException(error: String) : Exception("Something went wrong...\n$error")
class ConnectionTimeoutException : Exception("No internet connection")
class ApiException(val apiError: ApiError) : Exception(apiError.msg)
