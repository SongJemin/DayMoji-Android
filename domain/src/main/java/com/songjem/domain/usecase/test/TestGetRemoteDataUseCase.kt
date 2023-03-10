package com.songjem.domain.usecase.test

import com.songjem.domain.model.EmotionReportItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Single
import javax.inject.Inject

class TestGetRemoteDataUseCase
@Inject constructor(private val emotionRepository: EmotionRepository) {
    operator fun invoke(
    ) : Single<List<EmotionReportItem>> = emotionRepository.getRemoteTestDatas()
}