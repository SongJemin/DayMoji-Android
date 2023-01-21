package com.songjem.data.mapper

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.songjem.data.datasource.remote.response.SentimentAnalysisResponse
import com.songjem.domain.model.DailyEmotion

fun mapperToAnalysis(sentimentAnalysisResponse: SentimentAnalysisResponse): DailyEmotion {
    val emotionStatus = sentimentAnalysisResponse.document.sentiment
    val emotionDetail = sentimentAnalysisResponse.document.confidence
    return DailyEmotion(
        CalendarDay.from(2023, 1, 21),
        emotionStatus,
        DailyEmotion.EmotionDetail(
            emotionDetail.negative,
            emotionDetail.positive,
            emotionDetail.neutral
        )
    )
}