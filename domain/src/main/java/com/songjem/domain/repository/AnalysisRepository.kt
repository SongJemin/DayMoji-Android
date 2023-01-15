package com.songjem.domain.repository

import com.songjem.domain.model.DailyEmotion
import com.songjem.domain.model.SentimentAnalysisItem
import io.reactivex.Flowable

interface AnalysisRepository {
    fun reqSentimentAnalysis(
        sentimentAnalysisItem: SentimentAnalysisItem
    ) : Flowable<DailyEmotion>
}