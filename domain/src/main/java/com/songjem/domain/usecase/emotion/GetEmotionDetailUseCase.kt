package com.songjem.domain.usecase.emotion

import com.songjem.domain.model.EmotionReportItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetEmotionDetailUseCase
@Inject constructor(private val emotionRepository: EmotionRepository) {
    operator fun invoke(targetDate : String) : Flowable<EmotionReportItem?>? = emotionRepository.getEmotionReportDetail(targetDate)
}