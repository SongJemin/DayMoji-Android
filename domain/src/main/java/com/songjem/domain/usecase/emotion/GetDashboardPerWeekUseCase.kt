package com.songjem.domain.usecase.emotion

import com.songjem.domain.model.EmotionReportItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Single
import javax.inject.Inject

class GetDashboardPerWeekUseCase
@Inject constructor(private val emotionRepository: EmotionRepository) {
    operator fun invoke(startDate : String, endDate : String) : Single<List<EmotionReportItem>>
    = emotionRepository.getDashboardPerWeek(startDate, endDate)
}