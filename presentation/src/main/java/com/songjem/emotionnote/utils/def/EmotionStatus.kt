package com.songjem.emotionnote.utils.def

enum class EmotionStatus {
    HAPPY, ANGRY, SAD, SOSAD, SOSO, LOVE;

    companion object {
        fun getEmotionStatus(emotion: String): EmotionStatus {
            when (emotion) {
                "happy" -> {
                    return HAPPY
                }
                "angry" -> {
                    return ANGRY
                }
                "sad" -> {
                    return SAD
                }
                "sosad" -> {
                    return SOSAD
                }
                "soso" -> {
                    return SOSO
                }
                "love" -> {
                    return LOVE
                }
                else -> {
                    return HAPPY
                }
            }
        }
    }
}