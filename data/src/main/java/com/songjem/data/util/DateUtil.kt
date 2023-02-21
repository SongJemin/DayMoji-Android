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

    fun getDayOfWeek(targetDate: Date) : String {
        val cal = Calendar.getInstance()
        cal.time = targetDate

        val dayOfWeek = when(cal.get(Calendar.DAY_OF_WEEK)) {
            1 -> "일"
            2 -> "월"
            3 -> "화"
            4 -> "수"
            5 -> "목"
            6 -> "금"
            7 -> "토"
            else -> "일"
        }
        return dayOfWeek
    }
}