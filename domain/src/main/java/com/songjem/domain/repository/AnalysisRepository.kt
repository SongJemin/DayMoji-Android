package com.songjem.domain.repository

import com.songjem.domain.model.DailyEmotion
import com.songjem.domain.model.SentimentItem
import io.reactivex.Flowable

interface AnalysisRepository {
    fun reqSentimentAnalysis(
        sentimentItem: SentimentItem
    ) : Flowable<DailyEmotion>
}