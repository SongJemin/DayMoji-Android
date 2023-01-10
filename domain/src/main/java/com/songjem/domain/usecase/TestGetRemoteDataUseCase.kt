package com.songjem.domain.usecase

import com.songjem.domain.model.TestItem
import com.songjem.domain.repository.TestRepository
import io.reactivex.Single
import javax.inject.Inject

class TestGetRemoteDataUseCase
@Inject constructor(private val testRepository: TestRepository) {
    operator fun invoke(
    ) : Single<List<TestItem>> = testRepository.getRemoteTestDatas()
}