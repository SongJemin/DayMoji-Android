package com.songjem.data.datasource.local.dao

import androidx.room.*
import com.songjem.data.datasource.local.model.TestEntity
import com.songjem.domain.model.TestItem
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TestDao {

    @Query("SELECT * FROM testEntity")
    fun getAllTestData() : Single<List<TestEntity>>

    @Query("SELECT * FROM testEntity WHERE id LIKE :id AND " +
            "testVal LIKE :testVal LIMIT 1")
    fun getSearchTestData(id: String, testVal: String): TestEntity

    @Insert
    fun insertTestDatas(testDatas: List<TestEntity>) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTestData(testData: TestEntity) : Completable

    @Query("DELETE FROM testEntity")
    fun deleteAllTestDatas() : Completable
}