package com.example.osmdemo.features.trips_details.domain

import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.utils.BackendResult
import kotlinx.coroutines.flow.Flow

interface TripsDetailsRepository {

    suspend fun retrieveGisRoute(
        ctx: String,
        poly: Int? = null,
        polyEnc: String? = null
    ): Flow<BackendResult<List<Trip>>>

}