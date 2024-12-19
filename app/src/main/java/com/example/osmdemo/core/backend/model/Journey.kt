package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Journey(
    @SerializedName("Stops") val stops: Stops?,
    @SerializedName("JourneyDetailRef") val journeyDetailRef: JourneyDetailRef?,
    @SerializedName("Product") val product: Product?,
    @SerializedName("JourneyPath") val journeyPath: JourneyPath?,
    @SerializedName("name") val name: String?,
    @SerializedName("direction") val direction: String?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("directionFlag") val directionFlag: String?,
    @SerializedName("trainNumber") val trainNumber: String?,
    @SerializedName("trainCategory") val trainCategory: String?
) : Parcelable