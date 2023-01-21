package com.songjem.emotionnote.utils.calendar.emotion

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.songjem.emotionnote.R
import java.util.*

class SosoDayDecorator(dates: Collection<CalendarDay?>?, context: Context) :
    DayViewDecorator {
    private val dates: HashSet<CalendarDay>
    @SuppressLint("UseCompatLoadingForDrawables")
    val drawable: Drawable = context.resources.getDrawable(R.drawable.soso_calday_backgorund)

    init {
        this.dates = dates?.let { HashSet(it) }!!
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable)
    }
}