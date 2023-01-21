package com.songjem.emotionnote.utils.def

enum class EmotionStatus {
    HAPPY, ANGRY, SAD, SOSAD, SOSO, LOVE;

    companion object {
        fun getEmotionStatus(emotion: String): EmotionStatus {
            when (emotion) {
                "행복함" -> {
                    return HAPPY
                }
                "화남" -> {
                    return ANGRY
                }
                "슬픔" -> {
                    return SAD
                }
                "살짝슬픔" -> {
                    return SOSAD
                }
                "그저그럼" -> {
                    return SOSO
                }
                "사랑스러움" -> {
                    return LOVE
                }
                else -> {
                    return HAPPY
                }
            }
        }
    }
}