package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Note(
    @SerializedName("value") val value: String?,
    @SerializedName("key") val key: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("priority") val priority: Int?,
    @SerializedName("txtN") val txtN: String?
) : Parcelable