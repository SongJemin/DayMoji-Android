package com.songjem.emotionnote.presentation.main.calendar

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.songjem.domain.model.DailyEmotion
import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseFragment
import com.songjem.emotionnote.databinding.FragmentCalendarBinding
import com.songjem.emotionnote.utils.def.EmotionStatus
import com.songjem.emotionnote.utils.def.EmotionStatus.Companion.getEmotionStatus
import com.songjem.emotionnote.utils.calendar.*
import com.songjem.emotionnote.utils.calendar.emotion.*

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {

    private val happyDayList : ArrayList<CalendarDay> = ArrayList()
    private val angryDayList : ArrayList<CalendarDay> = ArrayList()
    private val sadDayList : ArrayList<CalendarDay> = ArrayList()
    private val soSadDayList : ArrayList<CalendarDay> = ArrayList()
    private val sosoDayList : ArrayList<CalendarDay> = ArrayList()
    private val loveDayList : ArrayList<CalendarDay> = ArrayList()

    private val testEmotions = listOf<String>("happy", "angry", "sad", "sosad", "soso", "love")

    override fun initView() {
        binding.apply {

            val dailyEmotionList = ArrayList<DailyEmotion>()
            for(i in 0 .. 31) {
                dailyEmotionList.add(DailyEmotion(CalendarDay.from(2023, 0, i), testEmotions[i % 6], DailyEmotion.EmotionDetail(0.1f, 0.9f, 0.0f)))
            }
//            dailyEmotionList.add(DailyEmotion(CalendarDay.from(2023, 0, 21), "happy", DailyEmotion.EmotionDetail(0.1f, 0.9f, 0.0f)))
//            dailyEmotionList.add(DailyEmotion(CalendarDay.from(2023, 0, 20), "angry", DailyEmotion.EmotionDetail(0.1f, 0.9f, 0.0f)))
//            dailyEmotionList.add(DailyEmotion(CalendarDay.from(2023, 0, 19), "sad", DailyEmotion.EmotionDetail(0.1f, 0.9f, 0.0f)))
//            dailyEmotionList.add(DailyEmotion(CalendarDay.from(2023, 0, 18), "soso", DailyEmotion.EmotionDetail(0.1f, 0.9f, 0.0f)))
//            dailyEmotionList.add(DailyEmotion(CalendarDay.from(2023, 0, 17), "happy", DailyEmotion.EmotionDetail(0.1f, 0.9f, 0.0f)))
//            dailyEmotionList.add(DailyEmotion(CalendarDay.from(2023, 0, 16), "sosad", DailyEmotion.EmotionDetail(0.1f, 0.9f, 0.0f)))
//            dailyEmotionList.add(DailyEmotion(CalendarDay.from(2023, 0, 15), "love", DailyEmotion.EmotionDetail(0.1f, 0.9f, 0.0f)))

            dailyEmotionList.forEach {
                when(getEmotionStatus(it.emotionStatus)) {
                    EmotionStatus.HAPPY -> {
                        happyDayList.add(it.date)
                    }
                    EmotionStatus.ANGRY -> {
                        angryDayList.add(it.date)
                    }
                    EmotionStatus.SAD -> {
                        sadDayList.add(it.date)
                    }
                    EmotionStatus.SOSAD -> {
                        soSadDayList.add(it.date)
                    }
                    EmotionStatus.SOSO -> {
                        sosoDayList.add(it.date)
                    }
                    EmotionStatus.LOVE -> {
                        loveDayList.add(it.date)
                    }
                }
            }

            val dayBackgroundDecorator = DayBackgroundDecorator(requireContext())
            val happyDayDecorator = HappyDayDecorator(happyDayList, requireContext())
            val angryDayDecorator = AngryDayDecorator(angryDayList, requireContext())
            val sadDayDecorator = SadDayDecorator(sadDayList, requireContext())
            val soSadDayDecorator = SoSadDayDecorator(soSadDayList, requireContext())
            val sosoDayDecorator = SosoDayDecorator(sosoDayList, requireContext())
            val loveDayDecorator = LoveDayDecorator(loveDayList, requireContext())

            val sundayDecorator = SundayDecorator()
            val saturdayDecorator = SaturdayDecorator()
            val todayDecorator = TodayDecorator(requireContext())

            binding.cvReportCalendar.addDecorators(dayBackgroundDecorator, happyDayDecorator, angryDayDecorator, sadDayDecorator
                , soSadDayDecorator, sosoDayDecorator, loveDayDecorator, sundayDecorator, saturdayDecorator, todayDecorator)
        }
    }
}