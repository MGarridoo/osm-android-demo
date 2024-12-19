package com.example.osmdemo.features.map.presentation

sealed interface MapEvent {
    data class LoadMap(val test: String) : MapEvent

    data class RetrieveGisRoute(val ctx: String) : MapEvent

    data class JourneyPos(
        val llLon: Double,
        val llLat: Double,
        val urLon: Double,
        val urLat: Double,
        val jid: String ? = null,
        val lines: String ? = null,
        val date: String ? = null,
        val time: String ? = null,
    ) : MapEvent

}