package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Names(
    @SerializedName("Name") val name: List<Name>
) : Parcelable

