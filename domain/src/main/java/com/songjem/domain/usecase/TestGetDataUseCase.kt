package com.songjem.domain.usecase

import com.songjem.domain.model.TestItem
import com.songjem.domain.repository.TestRepository
import io.reactivex.Flowable
import javax.inject.Inject

class TestGetDataUseCase
@Inject constructor(private val testRepository: TestRepository) {
    operator fun invoke(
    ) : Flowable<List<TestItem>> = testRepository.getAllTestData()
}