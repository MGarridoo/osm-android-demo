package com.example.osmdemo.shared.extensions

import android.graphics.Color

fun String.toColorInt(defaultColor: Int = Color.RED): Int {
    return try {
        Color.parseColor(this)
    } catch (e: Exception) {
        defaultColor
    }
}