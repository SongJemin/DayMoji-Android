package com.songjem.emotionnote.presentation.main.calendar

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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

    private var happyDayList : ArrayList<CalendarDay> = ArrayList()
    private var angryDayList : ArrayList<CalendarDay> = ArrayList()
    private var sadDayList : ArrayList<CalendarDay> = ArrayList()
    private var soSadDayList : ArrayList<CalendarDay> = ArrayList()
    private var sosoDayList : ArrayList<CalendarDay> = ArrayList()
    private var loveDayList : ArrayList<CalendarDay> = ArrayList()
    private val emotionSaveDay = BooleanArray(32)

    private lateinit var getResult : ActivityResultLauncher<Intent>

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.apply {
            cvReportCalendar.setOnDateChangedListener { widget, date, selected ->
                cvReportCalendar.selectedDate = date
                getDailyEmotionDetail(date)
            }

            cvReportCalendar.setOnMonthChangedListener { widget, date ->
                Log.d("songjem", "month click = $date")
                val year = date.year.toString()
                val month = if ((date.month + 1) <= 9) "0" + (date.month + 1) else (date.month + 1).toString()

                changeYearMonth(year + month)
            }

            btnAddRecordCalendar.setOnClickListener {
                val intent = Intent(context, RecordActivity::class.java)
                val targetDate = transTargetDateString(cvReportCalendar.selectedDate)
                intent.putExtra("targetDate", targetDate)
                getResult.launch(intent)
            }

            getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if(it.resultCode == RESULT_OK) {
                    Log.d("songjem", "기록 추가 후 캘린더로 리턴, date = " + it.data!!.getStringExtra("selectedDate"))
                    val addDate = it.data!!.getStringExtra("selectedDate")
                    val selectedDate = CalendarDay.from(addDate!!.substring(0,4).toInt(), addDate.substring(4,6).toInt()-1,
                        addDate.substring(6,8).toInt())
                    refreshCal(selectedDate)
                }
            }

            btnEditRecordCalendar.setOnClickListener {
                val intent = Intent(activity, RecordActivity::class.java)
                val year = cvReportCalendar.selectedDate.year.toString()
                val month = if(cvReportCalendar.selectedDate.month + 1 <= 9) ("0" + (cvReportCalendar.selectedDate.month + 1)) else (cvReportCalendar.selectedDate.month + 1).toString()
                val day = if(cvReportCalendar.selectedDate.day <= 9) ("0" + cvReportCalendar.selectedDate.day + 1) else cvReportCalendar.selectedDate.day.toString()
                intent.putExtra("targetDate", year + month + day)
                startActivity(intent)
            }

            btnDeleteRecordCalendar.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                    .setMessage("감정 기록을 삭제할까요?")
                    .setPositiveButton("확인"
                    ) { _, _ ->
                        val targetDate = transTargetDateString(cvReportCalendar.selectedDate)
                        deleteEmotionReport(targetDate)
                    }
                    .setNegativeButton("취소"
                    ) { _, _ ->
                    }
                builder.show()

            }
        }
        refreshCal(CalendarDay.today())

        viewModel.emotionMonthlyReport.observe(this) { monthlyList ->
            clearDecorators()

            monthlyList.forEach { list ->
                val date = CalendarDay.from(list.targetDate.substring(0,4).toInt(), list.targetDate.substring(4,6).toInt()-1,
                    list.targetDate.substring(6,8).toInt())
                Log.d("songjem", "monthlyDate, targetDate = $date" + ", emotionStatus = " + list.emotionStatus)
                emotionSaveDay[list.targetDate.substring(6,8).toInt()] = true
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
            setDecorate()
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

            binding.btnAddRecordCalendar.visibility = View.INVISIBLE
            binding.btnDeleteRecordCalendar.visibility = View.VISIBLE
            binding.btnEditRecordCalendar.visibility = View.VISIBLE
        }

        viewModel.noDataAlarm.observe(this) {
            clearDetailContent()
            binding.btnAddRecordCalendar.visibility = View.VISIBLE
            binding.btnDeleteRecordCalendar.visibility = View.INVISIBLE
            binding.btnEditRecordCalendar.visibility = View.INVISIBLE
        }

        viewModel.deleteDate.observe(this) { date ->
            Toast.makeText(context, "기록을 삭제하였습니다.", Toast.LENGTH_SHORT).show()

            val year = date!!.substring(0,4)
            var month = if ((date.substring(4,6).toInt() - 1) <= 9) "0" + (date.substring(4,6).toInt() - 1) else (date.substring(4,6).toInt() - 1).toString()
            val day = date.substring(6,8)
            val deleteDate = CalendarDay.from(year.toInt(), month.toInt(), day.toInt())

            binding.cvReportCalendar.removeDecorators()
            binding.cvReportCalendar.addDecorators(DayBackgroundDecorator(requireContext()))
            month = if (month.toInt() + 1 <= 9) "0" + (month.toInt() + 1) else (month.toInt() + 1).toString()
            viewModel.getEmotionReportMonthly(year + month)
            getDailyEmotionDetail(deleteDate)
        }

        viewModel.errorAlarm.observe(this) { msg ->
            Toast.makeText(context, "데이터 접근중 오류 발생, msg = $msg", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearDecorators() {
        binding.cvReportCalendar.removeDecorators()
        happyDayList = ArrayList()
        angryDayList = ArrayList()
        sadDayList = ArrayList()
        soSadDayList = ArrayList()
        sosoDayList = ArrayList()
        loveDayList = ArrayList()
    }

    private fun deleteEmotionReport(targetDate : String) {
        viewModel.deleteEmotionReport(targetDate)
    }

    private fun refreshCal(selectedDate: CalendarDay) {
        Log.d("songjem", "refreshCal = $selectedDate")
        binding.cvReportCalendar.selectedDate = selectedDate
        viewModel.getEmotionReportMonthly(transTargetDateString(binding.cvReportCalendar.selectedDate).substring(0, 6))
        getDailyEmotionDetail(selectedDate)
    }

    private fun changeYearMonth(yearMonth : String) {
        viewModel.getEmotionReportMonthly(yearMonth)
    }

    private fun setDecorate() {
        Log.d("songjem", "setCalendar Refresh")
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
        val month = if ((date.month + 1) <= 9) "0" + (date.month + 1) else (date.month + 1).toString()
        val day = if(date.day <= 9) ("0" + date.day) else date.day.toString()
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