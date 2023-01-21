package com.songjem.domain.model

import com.prolificinteractive.materialcalendarview.CalendarDay

data class DailyEmotion (
    val date : CalendarDay,
    val emotionStatus : String,
    val emotionDetail : EmotionDetail
) {
    data class EmotionDetail (
        val negative : Float,
        val positive : Float,
        val neutral : Float
    )
}