package net.insi8.coconut.api.extensions

import net.insi8.coconut.api.data.common.Result
import retrofit2.Response

/**
 *
 */
fun <T> Response<T>.toResult(): Result<T> {
    val data = this.body()
    return if (data != null) {
        Result.Success(data)
    } else {
        try {
            val error = this.errorBody()?.string()
            Result.Error(errorMessage = error)
        } catch (e: Exception) {
            Result.Error(errorMessage = e.message)
        }
    }
}