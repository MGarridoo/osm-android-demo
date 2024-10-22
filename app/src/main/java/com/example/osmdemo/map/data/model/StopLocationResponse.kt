package com.example.osmdemo.map.data.model

import com.google.gson.annotations.SerializedName

data class StopLocationWrapper(
    @SerializedName("StopLocation")
    val stopLocation: StopLocationResponse
)

data class StopLocationResponse(
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
) {
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
            val foregroundColor: Color,
            val backgroundColor: Color,
            val res: String
        ) {
            data class Color(
                val r: Int,
                val g: Int,
                val b: Int,
                val hex: String
            )
        }
    }
}








