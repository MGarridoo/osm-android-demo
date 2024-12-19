package com.example.osmdemo.core.backend.api

import com.example.osmdemo.core.backend.model.OSMPlace
import com.example.osmdemo.core.backend.utils.BackendResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NominatimAPI {

    @GET("search")
    suspend fun search(
        @Query("q") query: String? = null,
        @Query("format") format: String = "jsonv2",
        @Query("limit") limit: Int = 10,
        @Query("addressdetails") addressdetails: Int = 0,
        @Query("extratags") extratags: Int = 0,
        @Query("namedetails") namedetails: Int = 0,
        @Query("amenity") amenity: String? = null,
        @Query("street") street: String? = null,
        @Query("city") city: String? = null,
        @Query("county") county: String? = null,
        @Query("state") state: String? = null,
        @Query("country") country: String? = null,
        @Query("postalcode") postalcode: String? = null,
        @Query("countrycodes") countrycodes: String? = null,
        @Query("viewbox") viewbox: String? = null,
        @Query("bounded") bounded: Int? = null,
        @Query("polygon_geojson") polygonGeojson: Int? = null,
        @Query("polygon_kml") polygonKml: Int? = null,
        @Query("polygon_svg") polygonSvg: Int? = null,
        @Query("polygon_text") polygonText: Int? = null,
        @Query("polygon_threshold") polygonThreshold: Double? = null,
        @Query("email") email: String? = null,
        @Query("dedupe") dedupe: Int? = null,
        @Query("debug") debug: Int? = null,
    ): BackendResult<List<OSMPlace>>

}