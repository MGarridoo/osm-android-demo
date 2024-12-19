package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ResultStatus(
    @SerializedName("timeDiffCritical") val timeDiffCritical: Boolean?
) : Parcelable