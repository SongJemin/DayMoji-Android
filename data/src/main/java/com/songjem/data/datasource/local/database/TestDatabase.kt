package com.songjem.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.songjem.data.datasource.local.dao.TestDao
import com.songjem.data.datasource.local.model.TestEntity

@Database(entities = [TestEntity::class], version = 1)
abstract class TestDatabase : RoomDatabase() {
    abstract fun testDao() : TestDao
}