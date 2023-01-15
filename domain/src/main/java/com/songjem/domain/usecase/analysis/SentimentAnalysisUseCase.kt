package com.songjem.domain.usecase.analysis

import com.songjem.domain.model.DailyEmotion
import com.songjem.domain.model.SentimentItem
import com.songjem.domain.repository.AnalysisRepository
import io.reactivex.Flowable
import javax.inject.Inject

class SentimentAnalysisUseCase
@Inject constructor(private val analysisRepository: AnalysisRepository) {
    operator fun invoke(sentimentItem: SentimentItem) : Flowable<DailyEmotion> = analysisRepository.reqSentimentAnalysis(sentimentItem)
}