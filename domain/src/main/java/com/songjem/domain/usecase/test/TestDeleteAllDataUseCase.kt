package com.songjem.domain.usecase.test

import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Completable
import javax.inject.Inject

class TestDeleteAllDataUseCase
@Inject constructor(private val testRepository: EmotionRepository) {
    operator fun invoke() : Completable = testRepository.deleteAllLocalData()
}