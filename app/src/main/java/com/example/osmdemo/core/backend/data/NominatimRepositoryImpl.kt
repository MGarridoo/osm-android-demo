package com.example.osmdemo.core.backend.data

import com.example.osmdemo.core.backend.api.NominatimAPI
import com.example.osmdemo.core.backend.domain.NominatimRepository
import com.example.osmdemo.core.backend.model.OSMPlace
import com.example.osmdemo.core.backend.utils.BackendResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NominatimRepositoryImpl @Inject constructor(
    private val api: NominatimAPI
): NominatimRepository {

    override fun search(
        query: String?,
        format: String,
        limit: Int,
        addressdetails: Int,
        extratags: Int,
        namedetails: Int,
        amenity: String?,
        street: String?,
        city: String?,
        county: String?,
        state: String?,
        country: String?,
        postalcode: String?,
        countrycodes: String?,
        viewbox: String?,
        bounded: Int?,
        polygon_geojson: Int?,
        polygon_kml: Int?,
        polygon_svg: Int?,
        polygon_text: Int?,
        polygon_threshold: Double?,
        email: String?,
        dedupe: Int?,
        debug: Int?
    ): Flow<BackendResult<List<OSMPlace>>> {
        return flow {
            val result = api.search(
                query = query,
                format = format,
                limit = limit,
                addressdetails = addressdetails,
                extratags = extratags,
                namedetails = namedetails,
                amenity = amenity,
                street = street,
                city = city,
                county = county,
                state = state,
                country = country,
                postalcode = postalcode,
                countrycodes = countrycodes,
                viewbox = viewbox,
                bounded = bounded,
                polygonGeojson = polygon_geojson,
                polygonKml = polygon_kml,
                polygonSvg = polygon_svg,
                polygonText = polygon_text,
                polygonThreshold = polygon_threshold,
                email = email,
                dedupe = dedupe,
                debug = debug
            )
            when(result) {
                is BackendResult.Error -> { emit(BackendResult.Error(result.code, result.message)) }
                is BackendResult.Exception -> { emit(BackendResult.Exception(result.e)) }
                is BackendResult.Success -> { emit(BackendResult.Success(result.data)) }
            }
        }.catch { e ->
            emit(BackendResult.Exception(e))
        }
    }

}