package com.example.marvelcharacters.Utils


import java.text.SimpleDateFormat
import java.util.*

//TODO igual que el del modulo DATA.  Ver un sitio comun para...
object DateUtils {


    const val DATE_FORMAT = "dd/MM/yyyy HH:mm"


    fun getDateFormatted(date: Long?, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        if (date != null) {
            calendar.timeInMillis = date
        }
        return formatter.format(calendar.time)
    }

}
