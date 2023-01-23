package com.songjem.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.songjem.data.datasource.local.dao.EmotionDao
import com.songjem.data.datasource.local.model.EmotionReport

@Database(entities = [EmotionReport::class], version = 1)
abstract class EmotionDatabase : RoomDatabase() {
    abstract fun emotionDao() : EmotionDao
}