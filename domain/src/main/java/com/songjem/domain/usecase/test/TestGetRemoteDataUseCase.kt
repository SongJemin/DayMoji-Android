package com.songjem.domain.usecase.test

import com.songjem.domain.model.TestItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Single
import javax.inject.Inject

class TestGetRemoteDataUseCase
@Inject constructor(private val testRepository: EmotionRepository) {
    operator fun invoke(
    ) : Single<List<TestItem>> = testRepository.getRemoteTestDatas()
}