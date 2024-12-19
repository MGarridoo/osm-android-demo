package com.example.osmdemo.core.backend.domain

import com.example.osmdemo.core.backend.model.OSMPlace
import com.example.osmdemo.core.backend.utils.BackendResult
import kotlinx.coroutines.flow.Flow

interface NominatimRepository {

    fun search(
        query: String? = null,
        format: String = "jsonv2",
        limit: Int = 10,
        addressdetails: Int = 0,
        extratags: Int = 0,
        namedetails: Int = 0,
        amenity: String? = null,
        street: String? = null,
        city: String? = null,
        county: String? = null,
        state: String? = null,
        country: String? = null,
        postalcode: String? = null,
        countrycodes: String? = null,
        viewbox: String? = null,
        bounded: Int? = null,
        polygon_geojson: Int? = null,
        polygon_kml: Int? = null,
        polygon_svg: Int? = null,
        polygon_text: Int? = null,
        polygon_threshold: Double? = null,
        email: String? = null,
        dedupe: Int? = null,
        debug: Int? = null,
    ) : Flow<BackendResult<List<OSMPlace>>>


}