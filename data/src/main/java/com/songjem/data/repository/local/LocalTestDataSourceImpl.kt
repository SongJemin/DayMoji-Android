package com.songjem.data.repository.local

import com.songjem.data.datasource.local.dao.TestDao
import com.songjem.data.datasource.local.model.TestEntity
import com.songjem.domain.model.TestItem
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LocalTestDataSourceImpl @Inject constructor(private val testDao: TestDao)
    : LocalTestDataSource {

    override fun getLocalAllTestData(): Single<List<TestEntity>> = testDao.getAllTestData()

    override fun insertLocalTestDatas(testDatas: List<TestEntity>): Completable = testDao.insertTestDatas(testDatas)

    override fun insertLocalTestData(testData: TestEntity): Completable = testDao.insertTestData(testData)

    override fun deleteAllTestDatas(): Completable = testDao.deleteAllTestDatas()
}