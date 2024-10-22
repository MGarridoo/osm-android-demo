package com.example.osmdemo.map.data.model

import com.google.gson.annotations.SerializedName

data class NearbyStopsResponse(
    @SerializedName("stopLocationOrCoordLocation")
    val stopLocationOrCoordLocation: List<StopLocationWrapper>
) {
    data class StopLocationWrapper(
        @SerializedName("StopLocation")
        val stopLocation: StopLocation
    ) {
        data class StopLocation(
            @SerializedName("name")
            val name: String,

            @SerializedName("lon")
            val lon: Double,

            @SerializedName("lat")
            val lat: Double,

            @SerializedName("products")
            val products: Int,

            @SerializedName("dist")
            val distance: Int,

            @SerializedName("productAtStop")
            val productAtStop: List<ProductAtStop>,

            @SerializedName("extId")
            val extId: String,

            @SerializedName("minimumChangeDuration")
            val minimumChangeDuration: String
        ) {
            data class ProductAtStop(
                @SerializedName("name")
                val name: String,

                @SerializedName("line")
                val line: String,

                @SerializedName("catOut")
                val category: String,

                @SerializedName("icon")
                val icon: Icon
            ) {
                data class Icon(
                    @SerializedName("foregroundColor")
                    val foregroundColor: Color,

                    @SerializedName("backgroundColor")
                    val backgroundColor: Color,

                    @SerializedName("res")
                    val resource: String
                ) {
                    data class Color(
                        @SerializedName("r")
                        val red: Int,

                        @SerializedName("g")
                        val green: Int,

                        @SerializedName("b")
                        val blue: Int,

                        @SerializedName("hex")
                        val hex: String
                    )
                }
            }
        }
    }
}