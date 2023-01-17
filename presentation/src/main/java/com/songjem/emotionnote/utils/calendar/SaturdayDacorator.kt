package com.songjem.emotionnote.utils.calendar

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.time.DayOfWeek
import java.util.*

class SaturdayDecorator: DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val sunday = day?.date?.with(DayOfWeek.SATURDAY)?.dayOfMonth
        return sunday == day?.day
    }
    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object: ForegroundColorSpan(Color.BLUE){})
    }
}
