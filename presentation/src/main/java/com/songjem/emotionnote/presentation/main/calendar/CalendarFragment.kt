package com.songjem.emotionnote.presentation.main.calendar

import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseFragment
import com.songjem.emotionnote.databinding.FragmentCalendarBinding
import com.songjem.emotionnote.utils.calendar.EmotionDecorator
import com.songjem.emotionnote.utils.calendar.SaturdayDecorator
import com.songjem.emotionnote.utils.calendar.SundayDecorator
import com.songjem.emotionnote.utils.calendar.TodayDecorator

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {

    override fun initView() {
        binding.apply {

            val sundayDecorator = SundayDecorator()
            val saturdayDecorator = SaturdayDecorator()
            val todayDecorator = TodayDecorator(requireContext())
            val emotionDecorator = EmotionDecorator(requireContext())

            binding.cvReportCalendar.addDecorators(sundayDecorator, saturdayDecorator, todayDecorator, emotionDecorator)
        }
    }
}