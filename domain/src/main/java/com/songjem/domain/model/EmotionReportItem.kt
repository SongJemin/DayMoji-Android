package com.songjem.domain.model

data class EmotionReportItem (
    val targetDate : String,
    val reportContent : String,
    val emotionStatus : String,
    val positive : Float? = null,
    val negative : Float? = null,
    val neutral : Float? = null,
    val score : Float? = null,
    val magnitude : Float? = null,
    val firstReportDate : String,
    val lastReportDate : String
)