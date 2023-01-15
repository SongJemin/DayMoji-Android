package com.songjem.data.repository.remote.naver

import com.songjem.data.datasource.remote.api.NaverApi
import com.songjem.data.datasource.remote.model.SentimentAnalysisRequest
import com.songjem.data.datasource.remote.response.SentimentAnalysisResponse
import io.reactivex.Flowable
import javax.inject.Inject

class AnalysisDataSourceImpl
    @Inject constructor(private val naverApi: NaverApi) : AnalysisDataSource {
    override fun getAnalysisData(request: SentimentAnalysisRequest) : Flowable<SentimentAnalysisResponse> {
        return naverApi.reqSentimentAnalysis(request.clientId, request.clientSecret, request.sentimentData)
    }
}