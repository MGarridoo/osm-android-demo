package com.example.osmdemo.core.backend

sealed class BackendResult<out T> {
    data class Success<out T>(val data: T) : BackendResult<T>()
    data class Error<out T>(val code: Int, val message: String?) : BackendResult<T>()
    data class Exception<out T>(val e: Throwable) : BackendResult<T>()
}

suspend fun <T : Any> BackendResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): BackendResult<T> = apply {
    if (this is BackendResult.Success<T>) {
        executable(data)
    }
}

suspend fun <T : Any> BackendResult<T>.onError(
    executable: suspend (code: Int, message: String?) -> Unit
): BackendResult<T> = apply {
    if (this is BackendResult.Error<T>) {
        executable(code, message)
    }
}

suspend fun <T : Any> BackendResult<T>.onException(
    executable: suspend (e: Throwable) -> Unit
): BackendResult<T> = apply {
    if (this is BackendResult.Exception<T>) {
        executable(e)
    }
}