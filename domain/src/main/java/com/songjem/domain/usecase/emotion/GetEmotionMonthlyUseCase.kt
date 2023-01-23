package com.songjem.domain.usecase.emotion

import com.songjem.domain.repository.EmotionRepository
import javax.inject.Inject

class GetEmotionMonthlyUseCase @Inject constructor(
    private val emotionRepository: EmotionRepository
){
    operator fun invoke(targetYearMonth : String) = emotionRepository.getEmotionReportMonthly(targetYearMonth)
}