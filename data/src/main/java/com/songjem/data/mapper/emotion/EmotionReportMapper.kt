package com.songjem.data.mapper.emotion

import com.songjem.data.datasource.local.model.EmotionReport
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
    val emotionReport = EmotionReportItem(
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
    return emotionReport
}