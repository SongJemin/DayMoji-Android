package com.songjem.domain.usecase.emotion

import com.songjem.domain.model.DashBoardEmotionItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Single
import javax.inject.Inject

class GetDashboardPerPeriodUseCase
@Inject constructor(private val emotionRepository: EmotionRepository) {
    operator fun invoke(startDate : String, endDate : String) : Single<List<DashBoardEmotionItem>>
    = emotionRepository.getDashboardPerPeriod(startDate, endDate)
}