package com.example.osmdemo.map.data.api

import com.example.osmdemo.map.data.model.TripResponse
import com.example.osmdemo.map.data.model.Locations
import com.example.osmdemo.core.backend.BackendResult
import com.example.osmdemo.map.data.model.NearbyStopsResponse
import com.example.osmdemo.map.data.model.StopResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface API {

    @GET("datainfo")
    suspend fun getDataInfo(
        @Query("accessId") accessId: String,
        @Query("format") format: String = "json"
    ): BackendResult<ResponseBody>

    @GET("datainfo")
    suspend fun getDataInfoWithBearer(
        @Header("Authorization") bearerToken: String,
        @Header("Accept") accept: String = "application/json"
    ): BackendResult<ResponseBody>

    @GET("tti")
    suspend fun getTimetableInfo(
        @Query("accessId") accessId: String,
        @Query("format") format: String = "json"
    ): BackendResult<ResponseBody>

    @GET("location.name")
    suspend fun searchLocationByName(
        @Query("input") input: String,
        @Query("type") type: String? = null,
        @Query("lang") lang: String = "es",
        @Query("withProducts") withProducts: Int = 256,
        @Query("accessId") accessId: String,
        @Query("format") format: String = "json"
    ): BackendResult<Locations>

    @GET("location.name")
    suspend fun searchLocationByNameWithProduct(
        @Query("input") input: String,
        @Query("products") products: Int,
        @Query("lang") lang: String,
        @Query("accessId") accessId: String
    ): BackendResult<ResponseBody>

    @GET("location.boundingbox")
    suspend fun searchLocationInBoundingBox(
        @Query("llLon") llLon: Double,
        @Query("llLat") llLat: Double,
        @Query("urLon") urLon: Double,
        @Query("urLat") urLat: Double,
        @Query("type") type: String? = null,
        @Query("products") products: Int? = null,
        @Query("accessId") accessId: String,
        @Query("format") format: String? = "json"
    ): BackendResult<Locations>

    @GET("location.nearbystops")
    suspend fun searchNearbyStops(
        @Query("originCoordLong") originCoordLong: Double,
        @Query("originCoordLat") originCoordLat: Double,
        @Query("accessId") accessId: String,
        @Query("format") format: String = "json"
    ): BackendResult<NearbyStopsResponse>

    @GET("departureBoard")
    suspend fun getDepartureBoard(
        @Query("time") time: String,
        @Query("products") products: Int,
        @Query("maxJourneys") maxJourneys: Int,
        @Query("passlist") passlist: Int = 0,
        @Query("accessId") accessId: String,
        @Query("lang") lang: String = "es",
        @Query("id") id: String
    ): BackendResult<ResponseBody>

    @GET("arrivalBoard")
    suspend fun getArrivalBoard(
        @Query("id") id: String,
        @Query("accessId") accessId: String
    ): BackendResult<ResponseBody>

    @GET("journeyDetail")
    suspend fun getJourneyDetails(
        @Query("id") journeyId: String,
        @Query("accessId") accessId: String,
        @Query("poly") poly: Int? = null,
        @Query("polyEnc") polyEnc: String?,
        @Query("lang") lang: String = "es",
        @Query("format") format: String = "json",
    ): BackendResult<StopResponse>

    @GET("trip")
    suspend fun searchTrip(
        @Query("originId") originId: String,
        @Query("destId") destId: String,
        @Query("time") time: String? = null,
        @Query("maxChange") maxChange: Int? = null,
        @Query("withFreq") withFreq: Int? = null,
        @Query("poly") poly: Int? = null,
        @Query("polyEnc") polyEnc: String? = null,
        @Query("accessId") accessId: String,
        @Query("lang") lang: String = "es",
        @Query("format") format: String = "json"
    ): BackendResult<TripResponse>

    @GET("trip")
    suspend fun searchTripDoorToDoor(
        @Query("originCoordLong") originCoordLong: Double,
        @Query("originCoordLat") originCoordLat: Double,
        @Query("destCoordLong") destCoordLong: Double,
        @Query("destCoordLat") destCoordLat: Double,
        @Query("originWalk") originWalk: String,
        @Query("destWalk") destWalk: String,
        @Query("time") time: String,
        @Query("accessId") accessId: String,
        @Query("lang") lang: String = "es",
        @Query("format") format: String = "json"
    ): BackendResult<Locations>

    @GET("gisroute")
    suspend fun getGISRoute(
        @Query("ctx") ctx: String,
        @Query("poly") poly: Int? = null,
        @Query("polyEnc") polyEnc: String? = null,
        @Query("accessId") accessId: String,
        @Query("lang") lang: String = "es",
        @Query("format") format: String = "json"
    ): BackendResult<TripResponse>
}
