package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stop(
    //@SerializedName("Notes") val notes: List<Notes>?,
    @SerializedName("altId") val altId: List<String>?,
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("extId") val extId: String?,
    @SerializedName("routeIdx") val routeIdx: Int?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("depPrognosisType") val depPrognosisType: String?,
    @SerializedName("depTime") val depTime: String?,
    @SerializedName("depDate") val depDate: String?,
    @SerializedName("depDir") val depDir: String?,
    @SerializedName("arrTime") val arrTime: String?,
    @SerializedName("arrDate") val arrDate: String?,
    @SerializedName("minimumChangeDuration") val minimumChangeDuration: String?
) : Parcelable