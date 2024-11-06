package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Icon(
    @SerializedName("foregroundColor") val foregroundColor: Color?,
    @SerializedName("backgroundColor") val backgroundColor: Color?,
    @SerializedName("res") val resource: String?
) : Parcelable