package com.songjem.data.datasource.remote.api

import com.songjem.data.datasource.remote.model.SentimentEntity
import com.songjem.data.datasource.remote.response.SentimentAnalysisResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface NaverApi {
    @POST("/sentiment-analysis/v1/analyze")
    fun reqSentimentAnalysis(
        @Body
        content : SentimentEntity
    ): Flowable<SentimentAnalysisResponse>
}