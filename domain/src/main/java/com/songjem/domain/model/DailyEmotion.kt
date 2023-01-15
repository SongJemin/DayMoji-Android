package com.songjem.domain.model

data class DailyEmotion (
    val date : String,
    val emotionStatus : String,
    val emotionDetail : EmotionDetail
) {
    data class EmotionDetail (
        val negative : Float,
        val positive : Float,
        val neutral : Float
    )
}