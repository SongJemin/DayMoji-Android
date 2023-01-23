package com.songjem.domain.usecase.emotion

import com.songjem.domain.model.EmotionReportItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Completable
import javax.inject.Inject

class InsertEmotionReportUseCase
@Inject constructor(private val emotionRepository: EmotionRepository) {
    operator fun invoke(
        emotionReportItem: EmotionReportItem
    ) : Completable = emotionRepository.insertLocalData(emotionReportItem)
}