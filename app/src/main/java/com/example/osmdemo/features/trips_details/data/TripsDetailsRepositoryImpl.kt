package com.example.osmdemo.features.trips_details.data

import com.example.osmdemo.core.backend.domain.BackendRepository
import com.example.osmdemo.core.backend.model.Trip
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.features.trips_details.domain.TripsDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TripsDetailsRepositoryImpl @Inject constructor(
    private val repo: BackendRepository,
): TripsDetailsRepository {

    override suspend fun retrieveGisRoute(
        ctx: String,
        poly: Int?,
        polyEnc: String?
    ): Flow<BackendResult<List<Trip>>> {
        return repo.retrieveGisRoute(ctx = ctx, poly = "$poly", polyEnc = "$polyEnc")
    }

}