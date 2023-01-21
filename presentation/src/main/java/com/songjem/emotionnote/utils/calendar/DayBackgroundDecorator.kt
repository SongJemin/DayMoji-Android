package com.songjem.emotionnote.utils.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.songjem.emotionnote.R
import java.util.*


class DayBackgroundDecorator(context: Context) :
    DayViewDecorator {

    @SuppressLint("UseCompatLoadingForDrawables")
    val drawable: Drawable = context.resources.getDrawable(R.drawable.default_calday_backgorund)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable)
        view.addSpan(object: ForegroundColorSpan(Color.BLACK){})
    }
}