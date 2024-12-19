package com.example.osmdemo.shared.extensions

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    fun Calendar.formatDateTime(isDeparture: Boolean): String {
        val now = Calendar.getInstance(TimeZone.getTimeZone("America/Bogota"))
        val yesterday = (Calendar.getInstance(TimeZone.getTimeZone("America/Bogota"))).apply {
            add(Calendar.DAY_OF_MONTH, -1)
        }
        val tomorrow = (Calendar.getInstance(TimeZone.getTimeZone("America/Bogota"))).apply {
            add(Calendar.DAY_OF_MONTH, 1)
        }

        val formattedTime = this.formatTime()
        val formattedDate = when {
            this.isSameDay(now) -> "Hoy"
            this.isSameDay(yesterday) -> "Ayer"
            this.isSameDay(tomorrow) -> "Mañana"
            else -> this.formatDate()
        }

        return "$formattedDate, $formattedTime"
    }

    fun Calendar.formatExactDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(this.time)
    }

    private fun Calendar.formatTime(): String {
        val hour = this.get(Calendar.HOUR).let { if (it == 0) 12 else it }
        val minute = this.get(Calendar.MINUTE)
        val amPm = if (this.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
        return "%02d:%02d %s".format(hour, minute, amPm)
    }

    private fun Calendar.formatDate(): String {
        val dateFormat = SimpleDateFormat("EEE dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(this.time)
    }

    fun Calendar.isSameDay(other: Calendar): Boolean {
        return this.get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
                this.get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)
    }

    fun Calendar.isTomorrow(other: Calendar): Boolean {
        return this.get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
                this.get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR) + 1
    }

    fun Calendar.isYesterday(other: Calendar): Boolean {
        return this.get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
                this.get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR) - 1
    }
}
