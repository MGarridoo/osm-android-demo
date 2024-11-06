package com.example.osmdemo.map.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OperatorInfo(
    @SerializedName("name") val name: String?,
    @SerializedName("nameS") val nameS: String?,
    @SerializedName("nameN") val nameN: String?,
    @SerializedName("nameL") val nameL: String?,
    @SerializedName("id") val id: String?
) : Parcelable