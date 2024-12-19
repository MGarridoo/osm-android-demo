package com.example.osmdemo.shared.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.core.content.ContextCompat

fun createResizedDrawable(context: Context, drawableId: Int, scaleFactor: Float): Drawable? {
    val originalDrawable = ContextCompat.getDrawable(context, drawableId) ?: return null

    val bitmap = when (originalDrawable) {
        is BitmapDrawable -> originalDrawable.bitmap // Si ya es un BitmapDrawable, usamos su bitmap
        is VectorDrawable -> {
            // Convertir el VectorDrawable a un Bitmap
            val bitmap = Bitmap.createBitmap(
                originalDrawable.intrinsicWidth,
                originalDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            originalDrawable.setBounds(0, 0, canvas.width, canvas.height)
            originalDrawable.draw(canvas)
            bitmap
        }
        else -> throw IllegalArgumentException("Drawable no compatible: $originalDrawable")
    }

    // Calcular el nuevo tamaño
    val newWidth = (bitmap.width * scaleFactor).toInt()
    val newHeight = (bitmap.height * scaleFactor).toInt()

    // Redimensionar el bitmap
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false)

    // Retornar el nuevo Drawable redimensionado
    return BitmapDrawable(context.resources, resizedBitmap)
}