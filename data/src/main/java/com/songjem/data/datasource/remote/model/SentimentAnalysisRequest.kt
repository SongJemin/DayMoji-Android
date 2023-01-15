package com.songjem.data.datasource.remote.model

data class SentimentAnalysisRequest (
    val clientId : String,
    val clientSecret : String,
    val sentimentData : SentimentData
) {
    data class SentimentData(
        val content: String
    )
}