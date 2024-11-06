package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Journey(
    @SerializedName("Stops") val stops: Stops?,
    @SerializedName("JourneyDetailRef") val journeyDetailRef: JourneyDetailRef?,
    @SerializedName("Product") val product: Product?,
    @SerializedName("name") val name: String?,
    @SerializedName("direction") val direction: String?,
    @SerializedName("directionFlag") val directionFlag: String?,
    @SerializedName("trainNumber") val trainNumber: String?,
    @SerializedName("trainCategory") val trainCategory: String?
) : Parcelable