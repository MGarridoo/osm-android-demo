package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class TechnicalMessage(
    @SerializedName("value") val value: String?,
    @SerializedName("key") val key: String?
) : Parcelable