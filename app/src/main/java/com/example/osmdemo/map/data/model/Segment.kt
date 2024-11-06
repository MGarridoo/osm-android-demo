package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Segment(
    @SerializedName("Notes") val notes: Notes?,
    @SerializedName("Edge") val edges: List<Edge>?,
    @SerializedName("name") val name: String?,
    @SerializedName("rType") val routeType: String?,
    @SerializedName("man") val man: String?,
    @SerializedName("manTx") val manTx: String?,
    @SerializedName("ori") val ori: String?,
    @SerializedName("polyS") val polyStart: Int?,
    @SerializedName("polyE") val polyEnd: Int?,
    @SerializedName("dist") val dist: Int?,
    @SerializedName("durS") val durS: String?
) : Parcelable