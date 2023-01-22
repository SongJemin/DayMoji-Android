package com.songjem.domain.usecase.test

import com.songjem.domain.model.EmotionReportItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Flowable
import javax.inject.Inject

class TestGetDataUseCase
@Inject constructor(private val emotionRepository: EmotionRepository) {
    operator fun invoke(
    ) : Flowable<List<EmotionReportItem>> = emotionRepository.getAllTestData()
}