package com.songjem.data.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EmotionReport")
data class EmotionReport (
    @PrimaryKey @ColumnInfo(name = "targetDate") val targetDate : String,
    @ColumnInfo(name = "reportContent") val reportContent : String,
    @ColumnInfo(name = "emotionStatus") val emotionStatus : String,
    @ColumnInfo(name = "positive") val positive : Float? = null,
    @ColumnInfo(name = "negative") val negative : Float? = null,
    @ColumnInfo(name = "neutral") val neutral : Float? = null,
    @ColumnInfo(name = "score") val score : Float? = null,
    @ColumnInfo(name = "magnitude") val magnitude : Float? = null,
    @ColumnInfo(name = "firstReportDate") val firstReportDate : String,
    @ColumnInfo(name = "lastReportDate") val lastReportDate : String,
    @ColumnInfo(name = "isSecretMode") val isSecretMode : Boolean
)