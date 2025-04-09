package uz.payme.data.utils

import com.google.gson.Gson
import retrofit2.Response

inline fun <T, R> Response<T>.toResult(gson: Gson, onSuccess: (T) -> Result<R>): Result<R> {
    return if (isSuccessful && body() != null) {
        onSuccess(body()!!)
    } else if (errorBody() != null) {
        val error = gson.fromJson(errorBody()?.string(), ErrorMessage::class.java)
        Result.failure(Exception(error.message))
    } else {
        Result.failure(Throwable(message()))
    }
}

suspend inline fun <T> safeCall(block: suspend () -> Result<T>): Result<T> {
    return try {
        block()
    } catch (e: Exception) {
        Result.failure(e)
    }
}

data class ErrorMessage(val message: String)

enum class Category(val value: String) {
    BUSINESS(value = "business"),
    TECHNOLOGY(value = "technology"),
    SPORTS(value = "sports"),
    HEALTH(value = "health"),
    ENTERTAINMENT(value = "entertainment"),
    SCIENCE(value = "science"),
}