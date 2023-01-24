package com.songjem.emotionnote.presentation.main.calendar

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseFragment
import com.songjem.emotionnote.databinding.FragmentCalendarBinding
import com.songjem.emotionnote.presentation.main.record.RecordActivity
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

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.apply {
            cvReportCalendar.selectedDate = CalendarDay.today()
            getDailyEmotionDetail(CalendarDay.today())
            cvReportCalendar.setOnDateChangedListener { widget, date, selected ->
                cvReportCalendar.selectedDate = date
                getDailyEmotionDetail(date)
            }

            btnAddRecordCalendar.setOnClickListener {
                val intent = Intent(context, RecordActivity::class.java)
                val targetDate = transTargetDateString(cvReportCalendar.selectedDate)
                intent.putExtra("targetDate", targetDate)
                startActivity(intent)
            }
        }

        viewModel.emotionMonthlyReport.observe(this) { monthlyList ->
            monthlyList.forEach { list ->
                val date = CalendarDay.from(list.targetDate.substring(0,4).toInt(), list.targetDate.substring(4,6).toInt()-1,
                    list.targetDate.substring(6,8).toInt())
                Log.d("songjem", "monthlyDate, targetDate = $date" + ", emotionStatus = " + list.emotionStatus)
                when (getEmotionStatus(list.emotionStatus)) {
                    EmotionStatus.HAPPY -> {
                        happyDayList.add(date)
                    }
                    EmotionStatus.ANGRY -> {
                        angryDayList.add(date)
                    }
                    EmotionStatus.SAD -> {
                        sadDayList.add(date)
                    }
                    EmotionStatus.SOSAD -> {
                        soSadDayList.add(date)
                    }
                    EmotionStatus.SOSO -> {
                        sosoDayList.add(date)
                    }
                    EmotionStatus.LOVE -> {
                        loveDayList.add(date)
                    }
                }
            }
            setCalendar()
        }

        viewModel.emotionReportListData.observe(this) { datas ->
            Log.d("songjem", "Load EmotionReport Datas = $datas")
        }

        viewModel.emotionReport.observe(this) { report ->
            Log.d("songjem", "Load EmotionReport One Data = $report")
            binding.tvDateCalendar.text = (report.targetDate).substring(0, 4) + ". " + (report.targetDate).substring(4, 6) + ". " + report.targetDate.substring(6, 8)
            binding.tvEmotionStatusCalendar.text = "감정상태 : ${report.emotionStatus}"
            binding.tvPositiveLevelCalendar.text = "긍정수치 : ${report.positive}"
            binding.tvNegativeLevelCalendar.text = "부정수치 : ${report.negative}"
            binding.tvNeutralLevelCalendar.text = "중립수치 : ${report.neutral}"
            binding.tvReportContentCalendar.text = report.reportContent
        }

        viewModel.noDataAlarm.observe(this) {
            clearDetailContent()
        }

        viewModel.errorAlarm.observe(this) { msg ->
            Toast.makeText(context, "데이터 접근중 오류 발생, msg = $msg", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setCalendar() {
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

        binding.cvReportCalendar.addDecorators(
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
    }

    @SuppressLint("SetTextI18n")
    private fun getDailyEmotionDetail(date: CalendarDay) {
        viewModel.getEmotionDetail(transTargetDateString(date))
    }

    private fun transTargetDateString(date: CalendarDay): String {
        val year = date.year.toString()
        val month =
            if ((date.month + 1) <= 9) "0" + (date.month + 1) else (date.month + 1).toString()
        val day = date.day.toString()
        return year + month + day
    }

    @SuppressLint("SetTextI18n")
    private fun clearDetailContent() {
        binding.tvDateCalendar.text = ""
        binding.tvEmotionStatusCalendar.text = ""
        binding.tvPositiveLevelCalendar.text = ""
        binding.tvNegativeLevelCalendar.text = ""
        binding.tvNeutralLevelCalendar.text = ""
        binding.tvReportContentCalendar.text = "No Data"
    }
}