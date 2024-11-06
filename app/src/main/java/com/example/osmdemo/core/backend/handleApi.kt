package com.example.osmdemo.core.backend

import retrofit2.HttpException
import retrofit2.Response

fun <T : Any> handleApi(response: Response<T>): BackendResult<T> {
    return try {
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