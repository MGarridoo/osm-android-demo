package com.example.osmdemo.map.data.model

import com.google.gson.annotations.SerializedName

data class CoordLocationWrapper(
    @SerializedName("CoordLocation")
    val stopLocation: CoordLocationResponse
)

data class CoordLocationResponse(
    val icon: Icon,
    val id: String,
    val extId: String,
    val name: String,
    val type: String,
    val lon: Double,
    val lat: Double,
    val alt: Int
) {
    data class Icon(
        @SerializedName("res")
        val resource: String
    )
}
