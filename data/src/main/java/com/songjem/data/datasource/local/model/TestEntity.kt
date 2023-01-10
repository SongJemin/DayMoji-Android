package com.songjem.data.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "testEntity")
data class TestEntity (
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "testVal") val testVal : String?
)