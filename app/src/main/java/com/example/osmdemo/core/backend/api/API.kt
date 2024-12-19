package com.example.osmdemo.core.backend.api

import com.example.osmdemo.core.backend.model.DepartureResponse
import com.example.osmdemo.core.backend.model.JourneyPosResponse
import com.example.osmdemo.core.backend.model.LocationResponse
import com.example.osmdemo.core.backend.model.TripResponse
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.core.backend.model.StopResponse
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
    ): BackendResult<LocationResponse>

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
    ): BackendResult<LocationResponse>

    @GET("location.nearbystops")
    suspend fun searchNearbyStops(
        @Query("originCoordLong") originCoordLong: Double,
        @Query("originCoordLat") originCoordLat: Double,
        @Query("accessId") accessId: String,
        @Query("format") format: String = "json"
    ): BackendResult<ResponseBody>

    @GET("departureBoard")
    suspend fun getDepartureBoard(
        @Query("id") id: String,
        @Query("date") date: String? = null,
        @Query("time") time: String? = null,
        @Query("maxJourneys") maxJourneys: Int? = 1,
        @Query("products") products: Int = 256,
        @Query("passlist") passlist: Int = 0,
        @Query("accessId") accessId: String,
        @Query("lang") lang: String = "es",
        @Query("format") format: String = "json"
    ): BackendResult<DepartureResponse>

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
        @Query("polyEnc") polyEnc: String? = null,
        @Query("lang") lang: String = "es",
        @Query("format") format: String = "json",
    ): BackendResult<StopResponse>

    @GET("trip")
    suspend fun searchTrip(
        @Query("originId") originId: String? = null,
        @Query("originCoordLat") originCoordLat: Double? = null,
        @Query("originCoordLong") originCoordLong: Double? = null,
        @Query("originCoordName") originCoordName: String? = null,
        @Query("destId") destId: String? = null,
        @Query("destCoordLat") destCoordLat: Double? = null,
        @Query("destCoordLong") destCoordLong: Double? = null,
        @Query("destCoordName") destCoordName: String? = null,
        @Query("time") time: String? = null,
        @Query("date") date: String? = null,
        @Query("groupFilter") groupFilter: String? = null,
        @Query("attributes") attributes: String? = null,
        @Query("products") products: Int? = null,
        @Query("totalWalk") totalWalk: String? = null,
        @Query("totalBike") totalBike: String? = null,
        @Query("totalCar") totalCar: String? = null,
        @Query("changeTimePercent") changeTimePercent: Int? = null,
        @Query("maxChange") maxChange: Int? = null,
        @Query("minChangeTime") minChangeTime: Int? = null,
        @Query("addChangeTime") addChangeTime: Int? = null,
        @Query("maxChangeTime") maxChangeTime: Int? = null,
        @Query("rtMode") rtMode: String? = null,
        @Query("poly") poly: Int? = 1,
        @Query("passlist") passlist: Int? = 1,
        @Query("accessId") accessId: String,
        @Query("searchForArrival") searchForArrival: Int? = 0,
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
    ): BackendResult<LocationResponse>

    @GET("gisroute")
    suspend fun getGISRoute(
        @Query("ctx") ctx: String,
        @Query("poly") poly: Int? = null,
        @Query("polyEnc") polyEnc: String? = null,
        @Query("accessId") accessId: String,
        @Query("lang") lang: String = "es",
        @Query("format") format: String = "json"
    ): BackendResult<TripResponse>

    @GET("journeypos")
    suspend fun journeyPos(
        @Query("llLat") llLat: Double,
        @Query("llLon") llLon: Double,
        @Query("urLat") urLat: Double,
        @Query("urLon") urLon: Double,
        @Query("maxJny") maxJny: Int? = null,
        @Query("periodSize") periodSize: Int? = 35000,
        @Query("periodStep") periodStep: Int? = 2000,
        @Query("positionMode") positionMode: String? = "CALC",
        @Query("accessId") accessId: String,
        @Query("lang") lang: String = "es",
        @Query("format") format: String = "json"
    ): BackendResult<JourneyPosResponse>


}
