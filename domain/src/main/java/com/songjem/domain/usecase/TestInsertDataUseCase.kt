package com.songjem.domain.usecase

import com.songjem.domain.model.TestItem
import com.songjem.domain.repository.TestRepository
import io.reactivex.Completable
import javax.inject.Inject

class TestInsertDataUseCase
@Inject constructor(private val testRepository: TestRepository) {
    operator fun invoke(
        testItem: TestItem
    ) : Completable = testRepository.insertLocalData(testItem)
}