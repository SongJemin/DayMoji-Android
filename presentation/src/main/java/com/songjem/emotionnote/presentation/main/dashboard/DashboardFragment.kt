package com.songjem.emotionnote.presentation.main.dashboard

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.songjem.data.util.DateUtil
import com.songjem.data.util.DateUtil.dateToString
import com.songjem.domain.model.DashBoardEmotionItem
import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseFragment
import com.songjem.emotionnote.databinding.FragmentDashboardBinding
import com.songjem.emotionnote.utils.chart.MyMarkerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {

    private lateinit var dashBoardEmotions : List<DashBoardEmotionItem>
    override val viewModel : DashboardViewModel by activityViewModels()

    // bottomSheetBehavior
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun initView() {
        setObserve()
        getDashboardPerWeek()
        initBottomSheet()
        setButtonClick()
    }

    private fun setButtonClick() {
        binding.apply {
            btnDateFilterDashboard.setOnClickListener {
                Log.d("songjem", "날짜 필터 버튼 선택")
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

            btnEmotionFilterDashboard.setOnClickListener {
                Log.d("songjem", "감정 필터 버튼 선택")
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        requireActivity().findViewById<Button>(R.id.btn_expand_dashboard_bottom_sheet).setOnClickListener {
            // BottomSheet의 최대 높이만큼 보여주기
            Log.d("songjem", "임의입력(확장) 버튼 선택")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        requireActivity().findViewById<Button>(R.id.btn_hide_dashboard_bottom_sheet).setOnClickListener {
            // BottomSheet 숨김
            Log.d("songjem", "내리기 버튼 선택")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        requireActivity().findViewById<Button>(R.id.btn_weekend_dashboard_bottom_sheet).setOnClickListener {
            Log.d("songjem", "일주일 조회 버튼 선택")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        requireActivity().findViewById<Button>(R.id.btn_month_dashboard_bottom_sheet).setOnClickListener {
            Log.d("songjem", "한달 조회 버튼 선택")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        requireActivity().findViewById<Button>(R.id.btn_year_dashboard_bottom_sheet).setOnClickListener {
            Log.d("songjem", "일년 조회 버튼 선택")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.apply {
            btnDateFilterDashboard.setOnClickListener {
                Log.d("songjem", "날짜 필터 버튼 선택")
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

            btnEmotionFilterDashboard.setOnClickListener {
                Log.d("songjem", "감정 필터 버튼 선택")
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun setObserve() {
        viewModel.dashboardEmotions.observe(this) { list ->
            dashBoardEmotions = list
            binding.apply {
                configureChartAppearance(mpLineDashboard)
                prepareChartData(createChartData(), mpLineDashboard)
            }
        }
    }

    // Persistent BottomSheet 초기화
    private fun initBottomSheet() {

        val bottomSheetLayout = requireActivity().findViewById<LinearLayout>(R.id.bottom_sheet_layout)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN // BottomSheet 숨김

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                // BottomSheetBehavior state에 따른 이벤트
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.d("MainActivity", "state: hidden")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Log.d("MainActivity", "state: expanded")
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.d("MainActivity", "state: collapsed")
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Log.d("MainActivity", "state: dragging")
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        Log.d("MainActivity", "state: settling")
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        Log.d("MainActivity", "state: half expanded")
                    }
                }

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    // PersistentBottomSheet 내부 버튼 click event
    private fun persistentBottomSheetEvent() {
// BottomSheet 숨김
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

    }

    private fun getDashboardPerWeek() {
        val currentDate = DateUtil.currentDate().dateToString("yyyyMMdd")
        val startDate = DateUtil.prevDateFromToday(count = -6).dateToString("yyyyMMdd")
        viewModel.getDashboardPerWeek(startDate, currentDate)
    }

    private fun prepareChartData(data: LineData, lineChart: LineChart) {
        lineChart.data = data // LineData 전달
        lineChart.invalidate() // LineChart 갱신해 데이터 표시
    }

    private fun createChartData(): LineData {
        val emotionEntries = ArrayList<Entry>()
        val chartData = LineData()

        for(i in dashBoardEmotions.indices) {
            Log.d("songjem", "emotionScore[" + i + "] = " + dashBoardEmotions[i].emotionScore)
            emotionEntries.add(Entry(i.toFloat(), dashBoardEmotions[i].emotionScore))
        }

        val lineDataSet = LineDataSet(emotionEntries, "감정수치")
        chartData.addDataSet(lineDataSet)
        lineDataSet.lineWidth = 3f
        lineDataSet.circleRadius = 6f
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawCircleHole(true)
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawHorizontalHighlightIndicator(false)
        lineDataSet.setDrawHighlightIndicators(false)
        lineDataSet.color = Color.rgb(255, 155, 155)
        lineDataSet.setCircleColor(Color.rgb(255, 155, 155))

        chartData.setValueTextSize(15f)
        return chartData
    }

    private fun configureChartAppearance(lineChart: LineChart) {
        lineChart.extraBottomOffset = 15f // 간격
        lineChart.description.isEnabled = false // chart 밑에 description 표시 유무

        val markerView = MyMarkerView(context, R.layout.custom_marker_view)
        markerView.chartView = lineChart
        lineChart.marker = markerView

        // Legend는 차트의 범례
        val legend = lineChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.form = Legend.LegendForm.CIRCLE
        legend.formSize = 10f
        legend.textSize = 13f
        legend.textColor = Color.parseColor("#A3A3A3")
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.yEntrySpace = 5f
        legend.isWordWrapEnabled = true
        legend.xOffset = 80f
        legend.yOffset = 20f
        legend.calculatedLineSizes

        // XAxis (아래쪽) - 선 유무, 사이즈, 색상, 축 위치 설정
        val xAxis = lineChart.xAxis
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM // x축 데이터 표시 위치
        xAxis.granularity = 1f
        xAxis.textSize = 14f
        xAxis.textColor = Color.rgb(118, 118, 118)
        xAxis.spaceMin = 0.1f // Chart 맨 왼쪽 간격 띄우기
        xAxis.spaceMax = 0.1f // Chart 맨 오른쪽 간격 띄우기

        // YAxis(Right) (왼쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        val yAxisLeft = lineChart.axisLeft
        yAxisLeft.textSize = 14f
        yAxisLeft.textColor = Color.rgb(163, 163, 163)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.axisLineWidth = 2f
        yAxisLeft.axisMinimum = -50f // 최솟값
        yAxisLeft.axisMaximum = 50f // 최댓값
        yAxisLeft.granularity = 1f

        // YAxis(Left) (오른쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        val yAxis = lineChart.axisRight
        yAxis.setDrawLabels(false) // label 삭제
        yAxis.textColor = Color.rgb(163, 163, 163)
        yAxis.setDrawAxisLine(false)
        yAxis.axisLineWidth = 2f
        yAxis.axisMinimum = -50f // 최솟값
        yAxis.axisMaximum = 50f // 최댓값
        yAxis.granularity = 1f

        val xDays = ArrayList<String>()
        for(i in dashBoardEmotions.indices){
            val month = dashBoardEmotions[i].targetDate.substring(4, 6)
            val monthXValue = if(month.toInt() < 10) month.substring(1)
            else month

            val day = dashBoardEmotions[i].targetDate.substring(6, 8)
            val dayXValue = if(day.toInt() < 10) day.substring(1)
            else day

            if(day.toInt() == 1) xDays.add(monthXValue + "/" + dayXValue + "일")
            else xDays.add(dayXValue + "일")
        }

        lineChart.xAxis.valueFormatter= IndexAxisValueFormatter(xDays)
    }
}