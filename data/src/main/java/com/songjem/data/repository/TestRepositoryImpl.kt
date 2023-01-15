package com.songjem.data.repository

import android.util.Log
import com.songjem.data.datasource.local.model.TestEntity
import com.songjem.data.mapper.mapperToTest
import com.songjem.data.repository.local.LocalTestDataSource
import com.songjem.data.repository.remote.RemoteTestDataSource
import com.songjem.domain.model.TestItem
import com.songjem.domain.repository.TestRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val localTestDataSource: LocalTestDataSource,
    private val remoteTestDataSource: RemoteTestDataSource
) : TestRepository {

    override fun getAllTestData(): Flowable<List<TestItem>> {
        return localTestDataSource.getLocalAllTestData()
            .onErrorReturn { listOf() }
            .flatMapPublisher { datas ->
                if(datas.isEmpty()) {
                    getRemoteTestDatas()
                        .toFlowable()
                        .onErrorReturn { listOf() }
                } else {
                    val localData = Single.just(mapperToTest(datas))
                    val remoteData = getRemoteTestDatas()
                        .onErrorResumeNext { localData }
                    Single.concat(localData, remoteData)
                }
            }
    }

    override fun getLocalTestDatas(): Flowable<List<TestItem>> {
        return localTestDataSource.getLocalAllTestData()
            .onErrorReturn { listOf() }
            .flatMapPublisher { localDatas ->
                Log.d("songjem", "localDatas = $localDatas")
                Flowable.just(mapperToTest(localDatas))
//                if(localDatas.isEmpty()) {
//                    Flowable.error(java.lang.IllegalStateException("LocalData is Empty"))
//                } else {
//                    Flowable.just(mapperToTest(localDatas))
//                }
            }
    }

    override fun getRemoteTestDatas(): Single<List<TestItem>> {
        return remoteTestDataSource.getRemoteAllTestData()
            .flatMap {
                localTestDataSource.insertLocalTestDatas(it.testDatas!!)
                    .andThen(Single.just(mapperToTest(it.testDatas)))
            }
    }

    override fun insertLocalData(testItem: TestItem): Completable {
        Log.d("songjem", "Insert testItem id = " + testItem.id + ", val = " + testItem.testVal)
        val testData = TestEntity(testItem.id, testItem.testVal)
//        val insertResult = localTestDataSource.insertLocalTestData(testData)
//        Log.d("songjem", "insertResult = $insertResult")
        return  localTestDataSource.insertLocalTestData(testData)
    }

    override fun deleteAllLocalData(): Completable {
        Log.d("songjem", "deleteAllLocalData")
        return localTestDataSource.deleteAllTestDatas()
    }
}