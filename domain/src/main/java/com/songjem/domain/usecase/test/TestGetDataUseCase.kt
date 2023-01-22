package com.songjem.domain.usecase.test

import com.songjem.domain.model.TestItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Flowable
import javax.inject.Inject

class TestGetDataUseCase
@Inject constructor(private val testRepository: EmotionRepository) {
    operator fun invoke(
    ) : Flowable<List<TestItem>> = testRepository.getAllTestData()
}