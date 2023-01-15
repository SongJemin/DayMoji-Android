package com.songjem.data.repository.remote.naver

import com.songjem.data.datasource.remote.api.NaverApi
import com.songjem.data.datasource.remote.model.SentimentEntity
import com.songjem.data.datasource.remote.response.SentimentAnalysisResponse
import io.reactivex.Flowable
import javax.inject.Inject

class AnalysisDataSourceImpl
    @Inject constructor(private val naverApi: NaverApi) : AnalysisDataSource {
    override fun getAnalysisData(sentimentEntity: SentimentEntity) : Flowable<SentimentAnalysisResponse> {
        return naverApi.reqSentimentAnalysis(sentimentEntity)
    }
}