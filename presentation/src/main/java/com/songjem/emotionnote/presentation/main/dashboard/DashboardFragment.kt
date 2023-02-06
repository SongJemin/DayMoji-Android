package com.songjem.emotionnote.presentation.main.dashboard

import android.graphics.Color
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.songjem.data.util.DateUtil
import com.songjem.data.util.DateUtil.dateToString
import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseFragment
import com.songjem.emotionnote.databinding.FragmentDashboardBinding
import com.songjem.emotionnote.utils.chart.MyMarkerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {

    override val viewModel : DashboardViewModel by activityViewModels()

    override fun initView() {
        binding.apply {
            configureChartAppearance(mpLineDashboard)
            prepareChartData(createChartData(), mpLineDashboard)
        }

        val currentDate = DateUtil.currentDate().dateToString("yyyyMMdd")
        val startDate = DateUtil.prevDateFromToday(count = -7).dateToString("yyyyMMdd")
        viewModel.getDashboardPerWeek(startDate, currentDate)
    }

    private fun prepareChartData(data: LineData, lineChart: LineChart) {
        lineChart.data = data // LineData 전달
        lineChart.invalidate() // LineChart 갱신해 데이터 표시
    }

    private fun createChartData(): LineData {
        val emotionEntries = ArrayList<Entry>()
        val chartData = LineData()

        // TEST 데이터 추출
        for (i in 0..6) {
            val emotionLevel = (Math.random() * 100.0).toFloat() - 50f // 감정수치 값
            emotionEntries.add(Entry(i.toFloat(), emotionLevel))
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

        // XAxis에 원하는 String 설정하기 (날짜)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // TEST 데이터
                return "17일"
            }
        }
    }
}