package com.songjem.data.mapper.emotion

import android.util.Log
import com.songjem.data.datasource.local.model.EmotionReport
import com.songjem.domain.model.DashBoardEmotionItem
import com.songjem.domain.model.EmotionReportItem

fun mapperToEmotionList(emotionReportEntities: List<EmotionReport>): List<EmotionReportItem> {
    return emotionReportEntities.toList().map {
        EmotionReportItem(
            it.targetDate,
            it.reportContent,
            it.emotionStatus,
            it.positive,
            it.negative,
            it.neutral,
            it.score,
            it.magnitude,
            it.firstReportDate,
            it.lastReportDate
        )
    }
}

fun mapperToEmotion(emotionReportEntity: EmotionReport): EmotionReportItem {
    return EmotionReportItem(
        emotionReportEntity.targetDate,
        emotionReportEntity.reportContent,
        emotionReportEntity.emotionStatus,
        emotionReportEntity.positive,
        emotionReportEntity.negative,
        emotionReportEntity.neutral,
        emotionReportEntity.score,
        emotionReportEntity.magnitude,
        emotionReportEntity.firstReportDate,
        emotionReportEntity.lastReportDate
    )
}

fun mapperToDashBoard(emotionReportEntities: List<EmotionReport>): List<DashBoardEmotionItem> {

    return emotionReportEntities.toList().map { report ->
        val emotionScore = if(report.positive != null && report.negative != null) {
            (report.positive - report.negative).toInt()
        } else if(report.score != null) {
            report.score.toInt()
        } else 0

        val neutralScore = if(report.neutral != null) report.neutral.toInt()
        else if(report.magnitude != null) report.magnitude.toInt()
        else 0

        Log.d("songjemt", "targetDate = " + report.targetDate + ", positive = " + report.positive + ", negative = " + report.negative + ", neutral = " + report.neutral)
        Log.d("songjem", "emotionScore = $emotionScore, neutralScore = $neutralScore")

        DashBoardEmotionItem(
            report.targetDate,
            emotionScore,
            neutralScore
        )
    }
}