package uz.payme.data.utils

import com.google.gson.Gson
import retrofit2.Response
import uz.payme.data.repository.impl.ErrorMessage

inline fun <T, R> Response<T>.toResult(gson: Gson, onSuccess: (T) -> Result<R>): Result<R> {
    return if (isSuccessful && body() != null) {
        onSuccess(body()!!)
    } else if (errorBody() != null) {
        val error = gson.fromJson(errorBody()!!.string(), ErrorMessage::class.java)
        Result.failure(Exception(error.message))
    } else {
        Result.failure(Throwable(message()))
    }
}