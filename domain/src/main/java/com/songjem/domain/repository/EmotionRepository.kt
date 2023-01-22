package com.songjem.domain.repository

import com.songjem.domain.model.TestItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface EmotionRepository {
    fun getAllTestData(
    ) : Flowable<List<TestItem>>

    fun getAllEmotionReport(
    ) : Flowable<List<TestItem>>

    fun getRemoteTestDatas(
    ) : Single<List<TestItem>>

    fun insertLocalData(
        testData: TestItem
    ) : Completable

    fun deleteAllLocalData() : Completable
}