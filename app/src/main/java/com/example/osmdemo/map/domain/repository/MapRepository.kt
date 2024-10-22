package com.example.osmdemo.map.domain.repository

import com.example.osmdemo.map.data.model.TripResponse
import com.example.osmdemo.map.data.model.Locations
import com.example.osmdemo.core.backend.BackendResult
import com.example.osmdemo.map.data.model.NearbyStopsResponse
import com.example.osmdemo.map.data.model.StopResponse
import okhttp3.ResponseBody

interface MapRepository {

    suspend fun getDataInfo(
        accessId: String,
        format: String = "json"
    ): BackendResult<ResponseBody>

    suspend fun getDataInfoWithBearer(
        bearerToken: String,
        accept: String = "application/json"
    ): BackendResult<ResponseBody>

    suspend fun getTimetableInfo(
        accessId: String,
        format: String = "json"
    ): BackendResult<ResponseBody>

    suspend fun searchLocationByName(
        input: String,
        type: String? = null,
        lang: String = "es",
        withProducts: Int = 256,
        accessId: String,
        format: String = "json"
    ): BackendResult<Locations>

    suspend fun searchLocationByNameWithProduct(
        input: String,
        products: Int,
        lang: String,
        accessId: String
    ): BackendResult<ResponseBody>

    suspend fun searchLocationInBoundingBox(
        llLon: Double,
        llLat: Double,
        urLon: Double,
        urLat: Double,
        type: String? = null,  // Optional
        products: Int? = null,  // Optional
        accessId: String,
        format: String? = "json"  // Optional
    ): BackendResult<Locations>

    suspend fun searchNearbyStops(
        originCoordLong: Double,
        originCoordLat: Double,
        accessId: String,
        format: String = "json"
    ): BackendResult<NearbyStopsResponse>

    suspend fun getDepartureBoard(
        time: String,
        products: Int,
        maxJourneys: Int,
        passlist: Int = 0,  // Optional with default value
        accessId: String,
        lang: String = "es",  // Optional with default value
        id: String
    ): BackendResult<ResponseBody>

    suspend fun getArrivalBoard(
        id: String,
        accessId: String
    ): BackendResult<ResponseBody>

    suspend fun getJourneyDetails(
        journeyId: String,
        poly: Int? = null,
        polyEnc: String? = null,
        accessId: String
    ): BackendResult<StopResponse>

    suspend fun searchTrip(
        originId: String,
        destId: String,
        time: String? = null,  // Optional
        maxChange: Int? = null,  // Optional
        withFreq: Int? = null,  // Optional
        poly: Int? = null, // Optional
        polyEnc: String? = null, // Optional
        accessId: String,
        lang: String = "es",
        format: String = "json"
    ): BackendResult<TripResponse>

    suspend fun searchTripDoorToDoor(
        originCoordLong: Double,
        originCoordLat: Double,
        destCoordLong: Double,
        destCoordLat: Double,
        originWalk: String,
        destWalk: String,
        time: String,
        accessId: String,
        lang: String = "es",
        format: String = "json"
    ): BackendResult<Locations>

    suspend fun getGISRoute(
        ctx: String,
        poly: Int? = null,
        polyEnc: String? = null,
        accessId: String,
        lang: String = "es",
        format: String = "json"
    ): BackendResult<TripResponse>
}
