package com.example.osmdemo.map.presentation

data class MapState(
    val isLoading: Boolean = false,
    val errorCode: Int? = null,
    val exception: Throwable? = null,
    val errorMessage: String? = null
)
