package com.example.osmdemo.core.di

import com.example.osmdemo.map.data.api.API
import com.example.osmdemo.map.data.repository.MapRepositoryImpl
import com.example.osmdemo.map.domain.repository.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMapRepository(api: API) : MapRepository {
        return MapRepositoryImpl(api = api)
    }

}