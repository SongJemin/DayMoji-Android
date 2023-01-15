package com.songjem.domain.usecase.test

import com.songjem.domain.repository.TestRepository
import io.reactivex.Completable
import javax.inject.Inject

class TestDeleteAllDataUseCase
@Inject constructor(private val testRepository: TestRepository) {
    operator fun invoke() : Completable = testRepository.deleteAllLocalData()
}