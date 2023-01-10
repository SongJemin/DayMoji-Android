package com.songjem.domain.repository

import com.songjem.domain.model.TestItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface TestRepository {
    fun getAllTestData(
    ) : Flowable<List<TestItem>>

    fun getLocalTestDatas(
    ) : Flowable<List<TestItem>>

    fun getRemoteTestDatas(
    ) : Single<List<TestItem>>

    fun insertLocalData(
        testData: TestItem
    ) : Completable
}