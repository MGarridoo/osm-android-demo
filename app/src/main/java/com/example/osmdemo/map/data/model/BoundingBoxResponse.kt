package com.example.osmdemo.map.data.model

import com.google.gson.annotations.SerializedName

data class BoundingBoxResponse(
    @SerializedName("stopLocationOrCoordLocation")
    val coordLocations: List<CoordLocationWrapper>
)




