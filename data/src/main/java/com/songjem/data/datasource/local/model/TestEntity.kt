package com.songjem.data.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "testEntity")
data class TestEntity (
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    @ColumnInfo(name = "testVal") val testVal : String?
)