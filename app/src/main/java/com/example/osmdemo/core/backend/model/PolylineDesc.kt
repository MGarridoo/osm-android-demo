package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class PolylineDesc(
    @SerializedName("crd") val crd: List<Double>?,
    @SerializedName("delta") val delta: Boolean?,
    @SerializedName("dim") val dim: Int?,
    @SerializedName("crdEncS") val crdEncS: String?,
    @SerializedName("crdEncYX") val crdEncYX: String?,
) : Parcelable