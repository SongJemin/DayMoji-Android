package com.songjem.emotionnote.presentation.main.calendar

import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseFragment
import com.songjem.emotionnote.databinding.FragmentCalendarBinding
import com.songjem.emotionnote.utils.calendar.SaturdayDacorator
import com.songjem.emotionnote.utils.calendar.SundayDecorator
import com.songjem.emotionnote.utils.calendar.TodayDecorator

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {

    override fun initView() {
        binding.apply {

            val sundayDecorator = SundayDecorator()
            val saturdayDecorator = SaturdayDacorator()
            val todayDecorator = TodayDecorator(context!!)

            binding.cvReportCalendar.addDecorators(sundayDecorator, saturdayDecorator, todayDecorator)
        }
    }
}