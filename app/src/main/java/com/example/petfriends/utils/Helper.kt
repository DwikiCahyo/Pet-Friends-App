package com.example.petfriends.utils

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
    fun getCurrentDay(): String {
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val day = Date()
        return dayFormat.format(day)
    }
    fun getCurrentHours(): String {
        val hoursFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val hours = Date()
        return hoursFormat.format(hours)
    }
}