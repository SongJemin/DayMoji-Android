package com.songjem.emotionnote.utils.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.songjem.emotionnote.R

class TodayDecorator(context: Context): DayViewDecorator {
    private var date = CalendarDay.today()
    @SuppressLint("UseCompatLoadingForDrawables")
    val drawable: Drawable = context.resources.getDrawable(R.drawable.calendar_today_border)
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }
    override fun decorate(view: DayViewFacade?) {
//        view?.setBackgroundDrawable(drawable)
    }
}