package com.example.newsapplication.data.remote

import com.example.newsapplication.utils.safeLog
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.RuntimeException
import kotlin.Exception


fun Throwable.toBaseException(): BaseException {
    return when (val throwable = this) {
        is BaseException -> throwable

        is IOException -> BaseException.toNetworkError(throwable)

        is HttpException -> {
            val response = throwable.response()
            val httpCode = throwable.code()

            if(response?.errorBody() == null){
                return BaseException.toHttpError(
                        httpCode =  httpCode,
                        response = response
                )
            }

            val serverErrorResponseBody = try {
                response.errorBody()?.string() ?: ""
            } catch (e: Exception){
                e.safeLog()
                ""
            }

            val serverErrorResponse =
                    try {
                        Moshi.Builder().build().adapter(ServerErrorResponse::class.java)
                                .fromJson(serverErrorResponseBody)
                    } catch (e: Exception) {
                        e.safeLog()
                        ServerErrorResponse()
                    }
            if(serverErrorResponse != null){
                BaseException.toServerError(
                        serverErrorResponse = serverErrorResponse,
                        response = response,
                        httpCode = httpCode
                )
            }else{
                BaseException.toHttpError(
                        response = response,
                        httpCode = httpCode
                )
            }
        }

        else -> BaseException.toUnexpectedError(throwable)
    }
}


class BaseException(
        val errorType: ErrorType,
        val serverErrorResponse: ServerErrorResponse? = null,
        val response: Response<*>? = null,
        val httpCode: Int = 0,
        cause: Throwable? = null
) : RuntimeException(cause?.message, cause) {

    override val message: String?
        get() = when(errorType) {
            ErrorType.HTTP -> response?.message()

            ErrorType.NETWORK -> cause?.message

            ErrorType.SERVER -> serverErrorResponse?.errors?.getOrNull(0)

            ErrorType.UNEXPECTED -> cause?.message
        }

    companion object {
        fun toHttpError(response: Response<*>?, httpCode: Int) =
                BaseException(
                        errorType = ErrorType.HTTP,
                        response = response,
                        httpCode = httpCode
                )

        fun toNetworkError(cause: Throwable) =
                BaseException(
                        errorType = ErrorType.NETWORK,
                        cause = cause
                )

        fun toServerError(
                serverErrorResponse: ServerErrorResponse,
                response: Response<*>?,
                httpCode: Int
        ) = BaseException(
                errorType = ErrorType.SERVER,
                serverErrorResponse = serverErrorResponse,
                response = response,
                httpCode = httpCode
        )

        fun toUnexpectedError(cause: Throwable) =
                BaseException(
                        errorType = ErrorType.UNEXPECTED,
                        cause = cause
                )
    }
}

/**
 * Identifies the error type which triggered a [BaseException]
 */
enum class ErrorType {
    /**
     * An [IOException] occurred while communicating to the server.
     */
    NETWORK,

    /**
     * A non-2xx HTTP status code was received from the server
     */
    HTTP,

    /**
     * A error server with code & message
     */
    SERVER,

    /**
     * An internal error occurred while attempting to execute a request. It is beset proctice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
}

// TODO update server error response
data class ServerErrorResponse(
        val message: String? = null,
        val errors: List<String>? = null
)
