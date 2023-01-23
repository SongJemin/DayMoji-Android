package com.songjem.domain.usecase.emotion

import com.songjem.domain.model.EmotionReportItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetEmotionDetailUseCase
@Inject constructor(private val emotionRepository: EmotionRepository) {
    operator fun invoke(targetDate : String) : Maybe<EmotionReportItem> = emotionRepository.getEmotionReportDetail(targetDate)
}