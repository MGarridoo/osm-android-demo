package com.example.osmdemo.core.backend

import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> handleApi(execute: suspend () -> Response<T>): BackendResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            BackendResult.Success(body)
        } else {
            BackendResult.Error(response.code(), response.message())
        }
    } catch (e: HttpException) {
        BackendResult.Error(e.code(), e.message())
    } catch (e: Throwable) {
        BackendResult.Exception(e)
    }
}