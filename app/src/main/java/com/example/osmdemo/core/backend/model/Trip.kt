package com.example.osmdemo.core.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Trip(
    @SerializedName("Origin") val origin: Location,
    @SerializedName("Destination") val destination: Location,
    @SerializedName("ServiceDays") val serviceDays: List<ServiceDays>?,
    @SerializedName("JourneyDetailRef") val journeyDetailRef: JourneyDetailRef?,
    @SerializedName("Freq") val freq: Freq?,
    @SerializedName("LegList") val legList: LegList,
    @SerializedName("calculation") val calculation: String,
    @SerializedName("TripStatus") val tripStatus: TripStatus,
    @SerializedName("tripId") val tripId: String?,
    @SerializedName("ctxRecon") val ctxRecon: String?,
    @SerializedName("duration") val duration: String?,
    @SerializedName("rtDuration") val rtDuration: String?,
    @SerializedName("checksum") val checksum: String?,
    @SerializedName("transferCount") val transferCount: Int?,
    @SerializedName("idx") val idx: Int?
) : Parcelable