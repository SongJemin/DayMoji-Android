package com.songjem.emotionnote.presentation.main.calendar

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.songjem.domain.model.DailyEmotion
import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseFragment
import com.songjem.emotionnote.databinding.FragmentCalendarBinding
import com.songjem.emotionnote.utils.def.EmotionStatus
import com.songjem.emotionnote.utils.def.EmotionStatus.Companion.getEmotionStatus
import com.songjem.emotionnote.utils.calendar.*
import com.songjem.emotionnote.utils.calendar.emotion.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {

    override val viewModel : CalendarViewModel by activityViewModels()

    private val happyDayList : ArrayList<CalendarDay> = ArrayList()
    private val angryDayList : ArrayList<CalendarDay> = ArrayList()
    private val sadDayList : ArrayList<CalendarDay> = ArrayList()
    private val soSadDayList : ArrayList<CalendarDay> = ArrayList()
    private val sosoDayList : ArrayList<CalendarDay> = ArrayList()
    private val loveDayList : ArrayList<CalendarDay> = ArrayList()

    private val reportDayEmotionMap = HashMap<CalendarDay, DailyEmotion>()
    private val tempEmotions = listOf<String>("행복함", "화남", "슬픔", "살짝슬픔", "그저그럼", "사랑스러움")
    private val tempPositiveLevels = listOf<Float>(0.73f, 0.01f, 0.33f, 0.41f, 0.52f, 0.99f)
    private val tempNegativeLevels = listOf<Float>(0.27f, 0.99f, 0.67f, 0.59f, 0.48f, 0.01f)
    private val tempNeutralLevels = listOf<Float>(0.35f, 0.01f, 0.32f, 0.52f, 0.73f, 0.05f)

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.apply {

            for (i in 0..31) {
                val date = CalendarDay.from(2023, 0, i)
                reportDayEmotionMap[CalendarDay.from(2023, 0, i)] =
                    DailyEmotion(
                        date,
                        tempEmotions[i % 6],
                        DailyEmotion.EmotionDetail(tempNegativeLevels[i % 6], tempPositiveLevels[i % 6], tempNeutralLevels[i % 6])
                    )
            }

            reportDayEmotionMap.forEach {
                when (getEmotionStatus(it.value.emotionStatus)) {
                    EmotionStatus.HAPPY -> {
                        happyDayList.add(it.value.date)
                    }
                    EmotionStatus.ANGRY -> {
                        angryDayList.add(it.value.date)
                    }
                    EmotionStatus.SAD -> {
                        sadDayList.add(it.value.date)
                    }
                    EmotionStatus.SOSAD -> {
                        soSadDayList.add(it.value.date)
                    }
                    EmotionStatus.SOSO -> {
                        sosoDayList.add(it.value.date)
                    }
                    EmotionStatus.LOVE -> {
                        loveDayList.add(it.value.date)
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

            cvReportCalendar.addDecorators(
                dayBackgroundDecorator,
                happyDayDecorator,
                angryDayDecorator,
                sadDayDecorator,
                soSadDayDecorator,
                sosoDayDecorator,
                loveDayDecorator,
                sundayDecorator,
                saturdayDecorator,
                todayDecorator
            )

            cvReportCalendar.selectedDate = CalendarDay.today()
            getDailyEmotionDetail(CalendarDay.today())
            cvReportCalendar.setOnDateChangedListener { widget, date, selected ->
                getDailyEmotionDetail(date)
            }
        }

        viewModel.emotionReportListData.observe(this) { datas ->
            Log.d("songjem", "Load EmotionReport Datas = $datas")
        }

        viewModel.emotionReport.observe(this) { report ->
            Log.d("songjem", "Load EmotionReport One Data = $report")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDailyEmotionDetail(date: CalendarDay) {
        binding.tvDateCalendar.text =
            date.year.toString() + ". " + (date.month + 1) + ". " + date.day

        val emotionStatus = reportDayEmotionMap[date]?.emotionStatus
        val positiveLevel = reportDayEmotionMap[date]?.emotionDetail?.positive
        val negativeLevel = reportDayEmotionMap[date]?.emotionDetail?.negative
        val neutralLevel = reportDayEmotionMap[date]?.emotionDetail?.neutral

        binding.tvEmotionStatusCalendar.text = "감정상태 : $emotionStatus"
        binding.tvPositiveLevelCalendar.text = "긍정수치 : $positiveLevel"
        binding.tvNegativeLevelCalendar.text = "부정수치 : $negativeLevel"
        binding.tvNeutralLevelCalendar.text = "중립수치 : $neutralLevel"

        if(date == CalendarDay.today()) {
            val tempReportContent = "오늘부터 연휴 시작이지만 개발 공부할 생각하니 맘이 심숭생숭하다ㅠㅠ"
            binding.tvReportContentCalendar.text = tempReportContent
        }
    }
}