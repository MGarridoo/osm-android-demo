package com.example.osmdemo.core.backend.model

import com.google.gson.annotations.SerializedName

data class Departure(
    @SerializedName("JourneyDetailRef")
    val journeyDetailRef: JourneyDetailRef,

    @SerializedName("JourneyStatus")
    val journeyStatus: String,

    @SerializedName("ProductAtStop")
    val productAtStop: ProductAtStop,

    @SerializedName("Product")
    val product: List<Product>,

    @SerializedName("altId")
    val altId: List<String>,

    @SerializedName("Stops")
    val stops: Stops,

    @SerializedName("name")
    val name : String,

    @SerializedName("type")
    val type : String,

    @SerializedName("stop")
    val stop : String,

    @SerializedName("stopid")
    val stopId : String,

    @SerializedName("stopExtIdval")
    val stopExtIdval: String,

    @SerializedName("lon")
    val lon: Double,

    @SerializedName("lat")
    val lat: Double,

    @SerializedName("prognosisType")
    val prognosisType: String,

    @SerializedName("time")
    val time: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("rtTime")
    val rtTime: String,

    @SerializedName("rtDate")
    val rtDate: String,

    @SerializedName("reachable")
    val reachable: Boolean,

    @SerializedName("direction")
    val direction: String,

    @SerializedName("directionFlag")
    val directionFlag: String,
)
