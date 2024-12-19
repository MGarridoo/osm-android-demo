package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Icon(
    @SerializedName("foregroundColor") val foregroundColor: Color?,
    @SerializedName("backgroundColor") val backgroundColor: Color?,
    @SerializedName("res") val resource: String?
) : Parcelable