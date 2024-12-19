package com.example.osmdemo.features.trip_search.domain

import com.example.osmdemo.core.backend.model.LocationResponse
import com.example.osmdemo.core.backend.model.StopOrCoordLocation
import com.example.osmdemo.core.backend.utils.BackendResult
import kotlinx.coroutines.flow.Flow

interface TripSearchRepository {
    fun locationName(location: String) : Flow<BackendResult<List<StopOrCoordLocation>>>
}