package com.example.osmdemo.core.backend.model

data class NominatimResult(
    val place_id: Long,               // ID único del lugar
    val osm_type: String,             // Tipo de objeto OSM (node, way, relation)
    val osm_id: Long,                 // ID del objeto OSM
    val lat: String,                  // Latitud del lugar como cadena
    val lon: String,                  // Longitud del lugar como cadena
    val display_name: String,         // Nombre completo del lugar (dirección completa)
    val type: String,                 // Tipo del lugar (por ejemplo: city, village, road)
    val importance: Double? = null,   // Nivel de importancia del lugar (opcional)
    val boundingbox: List<String>? = null // Bounding box para delimitar el área del lugar (opcional)
)