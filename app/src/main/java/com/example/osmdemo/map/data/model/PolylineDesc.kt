package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PolylineDesc(
    @SerializedName("crd") val crd: List<Double>?,
    @SerializedName("delta") val delta: Boolean?,
    @SerializedName("dim") val dim: Int?,
    @SerializedName("crdEncS") val crdEncS: String?
) : Parcelable