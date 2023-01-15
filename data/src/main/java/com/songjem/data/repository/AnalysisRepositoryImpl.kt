package com.songjem.data.repository

import android.util.Log
import com.songjem.data.datasource.remote.model.SentimentEntity
import com.songjem.data.mapper.mapperToAnalysis
import com.songjem.data.repository.remote.naver.AnalysisDataSource
import com.songjem.domain.model.DailyEmotion
import com.songjem.domain.model.SentimentItem
import com.songjem.domain.repository.AnalysisRepository
import io.reactivex.Flowable
import javax.inject.Inject

class AnalysisRepositoryImpl @Inject constructor(
    private val analysisDataSource: AnalysisDataSource,
) : AnalysisRepository {

    override fun reqSentimentAnalysis(sentimentItem: SentimentItem): Flowable<DailyEmotion> {
        val sentimentData = SentimentEntity(sentimentItem.content)
        Log.d("songjem", "sentiment = " + sentimentData.content)
        return analysisDataSource.getAnalysisData(sentimentData)
            .flatMap { response ->
                Flowable.just(mapperToAnalysis(response))
            }
    }
}