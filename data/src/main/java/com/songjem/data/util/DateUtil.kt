package com.songjem.data.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun Date.dateToString(format: String, local : Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, local)
        return formatter.format(this)
    }

    fun currentDate(): Date {
        return Calendar.getInstance().time
    }

    fun prevDateFromToday(count : Int) : Date {
        val cal = GregorianCalendar(Locale.KOREA)
        cal.time = currentDate()
        cal.add(Calendar.DATE, count)
        return cal.time
    }
}