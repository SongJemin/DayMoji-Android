package com.songjem.data.mapper.emotion

import com.songjem.data.datasource.local.model.EmotionReport
import com.songjem.domain.model.EmotionReportItem

fun mapperToTest(emotionReportEntities: List<EmotionReport>): List<EmotionReportItem> {
    return emotionReportEntities.toList().map {
        EmotionReportItem(
            it.index,
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