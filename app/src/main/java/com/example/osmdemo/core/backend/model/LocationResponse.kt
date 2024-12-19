package com.example.osmdemo.core.backend.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("stopLocationOrCoordLocation")
    val stopLocationOrCoordLocation: List<StopOrCoordLocation>? = null,

    @SerializedName("data")
    val data: DataWrapper? = null,

    @SerializedName("TechnicalMessages")
    val technicalMessages: TechnicalMessages? = null,

    @SerializedName("serverVersion")
    val serverVersion: String? = null,

    @SerializedName("dialectVersion")
    val dialectVersion: String? = null,

    @SerializedName("requestId")
    val requestId: String? = null,

    @SerializedName("statusRequest")
    val statusRequest : Boolean? = null,

    @SerializedName("httpCode")
    val httpCode: Int? = null,

    @SerializedName("moreInformation")
    val moreInformation: Any? = null,

    @SerializedName("dateResponse")
    val dateResponse: String? = null,
) {
    val locations: List<StopOrCoordLocation>
        get() = data?.stopLocationOrCoordLocation ?: stopLocationOrCoordLocation ?: emptyList()

    data class DataWrapper(
        @SerializedName("stopLocationOrCoordLocation")
        val stopLocationOrCoordLocation: List<StopOrCoordLocation>
    )
}
