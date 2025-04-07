package com.tapac1k.day.contract

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DayUtil {
    @SuppressLint("ConstantLocale")
    private val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.US) // "EEEE" gives full weekday name
    private val shortDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)

    fun getDayOfWeek(day: Long): String {
        return dayOfWeekFormat.format(day.toDate())
    }

    fun getFormattedDay(day: Long): String {
        return shortDateFormat.format(day.toDate())
    }

    private fun Long.toDate(): Date {
        return Date(this * 1000 * 60 * 60 * 24)
    }
}
