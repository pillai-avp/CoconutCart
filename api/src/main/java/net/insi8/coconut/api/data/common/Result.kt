package net.insi8.coconut.api.data.common

class ResultErrorException(message: String) : IllegalStateException(message)

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<T>(
        val errorMessage: String? = null,
        val errorCode: String? = null,
        val data: T? = null
    ) : Result<T>()

    fun asSuccessOrThrowIfNot(): T = when (this) {
        is Success -> this.data
        is Error -> throw ResultErrorException(this.errorMessage ?: "Result was an error")
    }

    fun asSuccessOrNull(): T? = when (this) {
        is Success -> this.data
        is Error -> null
    }

    companion object {
        fun withEmptySuccess(): Success<Unit> {
            return Success(Unit)
        }

        fun <T> successIfNotNull(data: T?): Result<T> = when (data) {
            null -> Error(errorMessage = null)
            else -> Success(data)
        }
    }
}