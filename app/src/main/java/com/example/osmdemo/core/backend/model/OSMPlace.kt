package com.example.osmdemo.core.backend.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OSMPlace(
    @SerializedName("place_id") val placeId: Int,
    @SerializedName("licence") val licence: String,
    @SerializedName("osm_type") val osmType: String,
    @SerializedName("osm_id") val osmId: Long,
    @SerializedName("lat") val lat: String,
    @SerializedName("lon") val lon: String,
    @SerializedName("category") val category: String,
    @SerializedName("type") val type: String,
    @SerializedName("place_rank") val placeRank: Int,
    @SerializedName("importance") val importance: Double,
    @SerializedName("addresstype") val addressType: String,
    @SerializedName("name") val name: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("boundingbox") val boundingBox: List<String>
) {
    val toStopOrCoordLocation: StopOrCoordLocation
        get() = StopOrCoordLocation(
            coordLocation = CoordLocation(
                icon = Icon(
                    foregroundColor = null,
                    backgroundColor = null,
                    resource = "loc_service"
                ),
                id = placeId.toString(),
                extId = osmId.toString(),
                name = displayName,
                type = type,
                lon = lon.toDoubleOrNull(),
                lat = lat.toDoubleOrNull(),
                alt = 0
            )
        )
}