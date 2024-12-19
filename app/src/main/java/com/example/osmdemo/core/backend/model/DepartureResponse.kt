package com.example.osmdemo.core.backend.model

import com.google.gson.annotations.SerializedName

data class DepartureResponse(
    @SerializedName("Departure")
    val departure: List<Departure>,

    @SerializedName("TechnicalMessages")
    val technicalMessages: TechnicalMessages,

    @SerializedName("serverVersion")
    val serverVersion: String,

    @SerializedName("dialectVersion")
    val dialectVersion: String,

    @SerializedName("planRtTs")
    val planRtTs: String,

    @SerializedName("requestId")
    val requestId: String,
)
