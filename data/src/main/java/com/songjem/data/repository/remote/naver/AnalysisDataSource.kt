package com.songjem.data.repository.remote.naver

import com.songjem.data.datasource.remote.model.SentimentEntity
import com.songjem.data.datasource.remote.response.SentimentAnalysisResponse
import io.reactivex.Flowable

interface AnalysisDataSource {
    fun getAnalysisData(
        sentimentEntity: SentimentEntity
    ): Flowable<SentimentAnalysisResponse>
}