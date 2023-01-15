package com.songjem.data.repository

import android.util.Log
import com.songjem.data.datasource.remote.model.SentimentAnalysisRequest
import com.songjem.data.mapper.mapperToAnalysis
import com.songjem.data.repository.remote.naver.AnalysisDataSource
import com.songjem.domain.model.DailyEmotion
import com.songjem.domain.model.SentimentAnalysisItem
import com.songjem.domain.repository.AnalysisRepository
import io.reactivex.Flowable
import javax.inject.Inject

class AnalysisRepositoryImpl @Inject constructor(
    private val analysisDataSource: AnalysisDataSource,
) : AnalysisRepository {

    override fun reqSentimentAnalysis(item: SentimentAnalysisItem): Flowable<DailyEmotion> {
        val sentimentAnalysisData = SentimentAnalysisRequest(item.clientId, item.clientSecret, SentimentAnalysisRequest.SentimentData(item.sentimentData.content))
        Log.d("songjem", "sentiment = $sentimentAnalysisData")
        return analysisDataSource.getAnalysisData(sentimentAnalysisData)
            .flatMap { response ->
                Flowable.just(mapperToAnalysis(response))
            }
    }
}