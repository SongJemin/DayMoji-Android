package com.songjem.domain.model

data class SentimentAnalysisItem (
    val clientId : String,
    val clientSecret : String,
    val sentimentData : SentimentData
) {
    data class SentimentData(
        val content: String
    )
}