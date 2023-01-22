package com.songjem.domain.usecase.test

import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteAllEmotionReportUseCase
@Inject constructor(private val emotionRepository: EmotionRepository) {
    operator fun invoke() : Completable = emotionRepository.deleteAllLocalData()
}