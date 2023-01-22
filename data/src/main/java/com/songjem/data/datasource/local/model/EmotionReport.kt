package com.songjem.data.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EmotionReport")
data class EmotionReport (
    @PrimaryKey(autoGenerate = true) val index : Int? = null,
    @ColumnInfo(name = "targetDate") val targetDate : String,
    @ColumnInfo(name = "reportContent") val reportContent : String,
    @ColumnInfo(name = "emotionStatus") val emotionStatus : String,
    @ColumnInfo(name = "positive") val positiveLevel : Float?,
    @ColumnInfo(name = "negative") val negativeLevel : Float?,
    @ColumnInfo(name = "neutral") val neutral : Float?,
    @ColumnInfo(name = "score") val score : Float?,
    @ColumnInfo(name = "magnitude") val magnitude : Float?,
    @ColumnInfo(name = "firstReportDate") val firstReportDate : String,
    @ColumnInfo(name = "lastReportDate") val lastReportDate : String
)