package com.songjem.domain.usecase.emotion

import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteEmotionReportUseCase
@Inject constructor(private val emotionRepository: EmotionRepository) {
    operator fun invoke(targetDate : String) : Completable = emotionRepository.deleteEmotionReport(targetDate)
}