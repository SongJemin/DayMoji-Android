package com.songjem.emotionnote.utils.calendar

import android.annotation.SuppressLint
import android.content.Context
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.songjem.emotionnote.R
import java.util.*

class EmotionDecorator(var context : Context) : DayViewDecorator {
    private var date = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(context.getDrawable(R.drawable.smile_emotion_icon)!!)
    }
}