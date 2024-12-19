package com.example.osmdemo.core.di

import com.example.osmdemo.BuildConfig
import com.example.osmdemo.core.backend.api.DevMaasAPI
import com.example.osmdemo.core.backend.api.HafasHaconAPI
import com.example.osmdemo.core.backend.api.NominatimAPI
import com.example.osmdemo.core.backend.utils.NetworkResultCallAdapterFactory
import com.example.osmdemo.core.backend.api.API
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

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
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }

    @Provides
    @Singleton
    fun provideHafasHaconAPI(retrofit: Retrofit) : HafasHaconAPI {
        return retrofit.create(HafasHaconAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDevMaasAPI(retrofit: Retrofit) : DevMaasAPI {
        return retrofit.create(DevMaasAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNominatimAPI(client: OkHttpClient) : NominatimAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .client(client)
            .build()
        return retrofit.create(NominatimAPI::class.java)
    }

}