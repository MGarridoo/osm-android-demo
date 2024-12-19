package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Edge(
    @SerializedName("edgeId") val edgeId: String?,
    @SerializedName("graphId") val graphId: String?
) : Parcelable