package com.songjem.domain.model

data class EmotionReportItem (
    val index : Int? = null,
    val targetDate : String,
    val reportContent : String,
    val emotionStatus : String,
    val positive : Float,
    val negative : Float,
    val neutral : Float,
    val score : Float,
    val magnitude : Float,
    val firstReportDate : String,
    val lastReportDate : String
)