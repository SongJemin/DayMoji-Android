package com.songjem.data.repository.local

import com.songjem.data.datasource.local.model.TestEntity
import com.songjem.domain.model.TestItem
import io.reactivex.Completable
import io.reactivex.Single

interface LocalTestDataSource {
    fun getLocalAllTestData() : Single<List<TestEntity>>
    fun insertLocalTestDatas(testDatas: List<TestEntity>): Completable
    fun insertLocalTestData(testData: TestEntity): Completable
    fun deleteAllTestDatas(): Completable
}