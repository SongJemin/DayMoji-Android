package com.songjem.data.datasource.remote.response

data class SentimentAnalysisResponse(
    val document: Document,
    val sentences: List<Sentence>
) {
    data class Document(
        val confidence: Confidence,
        val sentiment: String
    )
    data class Sentence(
        val confidence: Confidence,
        val content: String,
        val highlights: List<Highlight>,
        val length: String,
        val offset: String,
        val sentiment: String
    ) {
        data class Highlight(
            val length: String,
            val offset: String
        )
    }
}