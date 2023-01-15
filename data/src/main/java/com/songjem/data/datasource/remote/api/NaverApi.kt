package com.songjem.data.datasource.remote.api

import com.songjem.data.datasource.remote.model.SentimentAnalysisRequest
import com.songjem.data.datasource.remote.response.SentimentAnalysisResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NaverApi {
    @POST("/sentiment-analysis/v1/analyze")
    fun reqSentimentAnalysis(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId : String,
        @Header("X-NCP-APIGW-API-KEY") clientSecret : String,
        @Body
        content : SentimentAnalysisRequest.SentimentData
    ): Flowable<SentimentAnalysisResponse>
}