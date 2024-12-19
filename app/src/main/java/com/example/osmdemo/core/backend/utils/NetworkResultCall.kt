package com.example.osmdemo.core.backend.utils

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>
) : Call<BackendResult<T>> {

    override fun enqueue(callback: Callback<BackendResult<T>>) {
        proxy.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = handleApi(response)
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))

                /*CoroutineScope(Dispatchers.Main).launch {
                    val networkResult = handleApi { response }
                    callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
                }*/
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = BackendResult.Exception<T>(t)
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }

        })
    }

    override fun execute(): Response<BackendResult<T>> = throw NotImplementedError()
    override fun clone(): Call<BackendResult<T>> = NetworkResultCall(proxy.clone())
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() { proxy.cancel() }
}