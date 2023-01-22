package com.songjem.domain.usecase.test

import com.songjem.domain.model.TestItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Completable
import javax.inject.Inject

class TestInsertDataUseCase
@Inject constructor(private val testRepository: EmotionRepository) {
    operator fun invoke(
        testItem: TestItem
    ) : Completable = testRepository.insertLocalData(testItem)
}