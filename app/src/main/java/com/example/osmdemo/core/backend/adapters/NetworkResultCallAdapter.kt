package com.example.osmdemo.core.backend.adapters

import com.example.osmdemo.core.backend.BackendResult
import com.example.osmdemo.core.backend.NetworkResultCall
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResultCallAdapter(
  private val resultType: Type
) : CallAdapter<Type, Call<BackendResult<Type>>> {

  override fun responseType(): Type = resultType

  override fun adapt(call: Call<Type>): Call<BackendResult<Type>> {
      return NetworkResultCall(call)
  }
}