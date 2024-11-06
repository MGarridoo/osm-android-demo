package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    @SerializedName("Notes") val notes: Notes?,
    @SerializedName("altId") val altId: List<String>?,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: String,
    @SerializedName("extId") val extId: String?,
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double,
    @SerializedName("alt") val alt: Int?,
    @SerializedName("time") val time: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("minimumChangeDuration") val minimumChangeDuration: String?,
    @SerializedName("routeIdx") val routeIdx: Int?,
    @SerializedName("prognosisType") val prognosisType: String?,
    @SerializedName("reachable") val reachable: Boolean?,
    @SerializedName("waitingState") val waitingState: String?
) : Parcelable