package com.example.osmdemo.core.di

import com.example.osmdemo.map.data.model.LocationResponse
import LocationResponseAdapter
import com.example.osmdemo.core.backend.adapters.NetworkResultCallAdapterFactory
import com.example.osmdemo.map.data.api.API
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val cacheControlInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .header("Cache-Control", "no-cache")
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .addInterceptor(cacheControlInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient, /*gson: Gson*/): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(LocationResponse::class.java, LocationResponseAdapter())
                        .setLenient()
                        .create()
                )
            )
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .baseUrl("https://moovia.demo.hafas.cloud/restproxy/")
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }

}