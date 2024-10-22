package com.example.osmdemo.map.data.model

import com.google.gson.annotations.SerializedName

sealed class LocationResponse {

    data class StopLocation(
        val productAtStop: List<ProductAtStop>,
        val altId: List<String>,
        val timezoneOffset: Int,
        val id: String,
        val extId: String,
        val name: String,
        val lon: Double,
        val lat: Double,
        val weight: Int,
        val products: Int,
        val minimumChangeDuration: String
    ) : LocationResponse() {
        data class ProductAtStop(
            val icon: Icon,
            val name: String,
            val internalName: String,
            val line: String,
            val lineId: String,
            val catOut: String,
            val cls: String,
            val catOutS: String,
            val catOutL: String
        ) {
            data class Icon(
                val res: String
            )
        }
    }

    data class CoordLocation(
        val icon: Icon,
        val id: String,
        val extId: String,
        val name: String,
        val type: String,
        val lon: Double,
        val lat: Double,
        val alt: Int
    ) : LocationResponse() {
        data class Icon(
            val res: String
        )
    }
}

data class Locations(
    @SerializedName("stopLocationOrCoordLocation")
    val locations: List<LocationResponse>
)
