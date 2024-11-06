package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Leg(
    @SerializedName("Origin") val origin: Location,
    @SerializedName("Destination") val destination: Location,
    @SerializedName("GisRef") val gisRef: GisRef?,
    @SerializedName("GisRoute") val gisRoute: GisRoute?,
    @SerializedName("Product") val product: List<Product>?,
    @SerializedName("PolylineGroup") val polylineGroup: PolylineGroup?,
    @SerializedName("Stops") val stops: Stops?,
    @SerializedName("JourneyDetailRef") val journeyDetailRef: JourneyDetailRef?,
    @SerializedName("Freq") val freq: Freq?,
    @SerializedName("JourneyStatus") val journeyStatus: String?,
    @SerializedName("id") val id: String,
    @SerializedName("idx") val idx: Int,
    @SerializedName("name") val name: String,
    @SerializedName("number") val number: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("type") val type: String,
    @SerializedName("reachable") val reachable: Boolean?,
    @SerializedName("waitingState") val waitingState: String?,
    @SerializedName("direction") val direction: String?,
    @SerializedName("directionFlag") val directionFlag: String?,
    @SerializedName("duration") val duration: String,
    @SerializedName("minimumChangeDuration") val minimumChangeDuration: String?,
    @SerializedName("dist") val dist: Int?
) : Parcelable