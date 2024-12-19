package com.example.osmdemo.core.di

import com.example.osmdemo.BuildConfig
import com.example.osmdemo.core.backend.api.DevMaasAPI
import com.example.osmdemo.core.backend.api.HafasHaconAPI
import com.example.osmdemo.core.backend.api.NominatimAPI
import com.example.osmdemo.core.backend.data.BackendRepositoryDevMaasImpl
import com.example.osmdemo.core.backend.data.BackendRepositoryHaconImpl
import com.example.osmdemo.core.backend.data.NominatimRepositoryImpl
import com.example.osmdemo.core.backend.domain.BackendRepository
import com.example.osmdemo.core.backend.domain.NominatimRepository
import com.example.osmdemo.core.backend.api.API
import com.example.osmdemo.features.map.data.MapRepositoryImpl
import com.example.osmdemo.features.map.domain.MapRepository
import com.example.osmdemo.features.trip_planner.data.TripPlannerRepositoryImpl
import com.example.osmdemo.features.trip_planner.domain.TripPlannerRepository
import com.example.osmdemo.features.trip_search.data.TripSearchRepositoryImpl
import com.example.osmdemo.features.trip_search.domain.TripSearchRepository
import com.example.osmdemo.features.trips.data.TripsRepositoryImpl
import com.example.osmdemo.features.trips.domain.TripsRepository
import com.example.osmdemo.features.trips_details.data.TripsDetailsRepositoryImpl
import com.example.osmdemo.features.trips_details.domain.TripsDetailsRepository
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
    fun provideMapRepository(repository: BackendRepository) : MapRepository {
        return MapRepositoryImpl(repository)
    }

    @Provides
    @Singleton
    fun provideTripPlannerRepository(api: API, repository: BackendRepository) : TripPlannerRepository {
        return TripPlannerRepositoryImpl(api, repository)
    }

    @Provides
    @Singleton
    fun provideTripsRepository(repository: BackendRepository) : TripsRepository {
        return TripsRepositoryImpl(repository)
    }

    @Provides
    @Singleton
    fun provideTripSearch(
        repository: BackendRepository,
        nominatimAPI: NominatimRepository,
    ): TripSearchRepository {
        return TripSearchRepositoryImpl(repository, nominatimAPI)
    }

    @Provides
    @Singleton
    fun provideNominatimRepository(api: NominatimAPI) : NominatimRepository {
        return NominatimRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideTripsDetailsRepository(repository: BackendRepository) : TripsDetailsRepository {
        return TripsDetailsRepositoryImpl(repository)
    }

    @Provides
    @Singleton
    fun provideBackendApi(
        devApi: DevMaasAPI,
        hafasApi: HafasHaconAPI
    ): BackendRepository {
        return if (BuildConfig.FLAVOR == "dev") {
            BackendRepositoryDevMaasImpl(devApi)
        } else {
            BackendRepositoryHaconImpl(hafasApi)
        }
    }

}