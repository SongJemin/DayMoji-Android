package com.songjem.data.datasource.local.dao

import androidx.room.*
import com.songjem.data.datasource.local.model.EmotionReport
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface EmotionDao {

    @Query("SELECT * FROM EmotionReport")
    fun getAllEmotionReport() : Single<List<EmotionReport>>

    @Query("SELECT * FROM EmotionReport WHERE targetDate LIKE :targetYearMonth || '%'")
    fun getEmotionReportMonthly(targetYearMonth : String) : Single<List<EmotionReport>>

    @Query("SELECT * FROM EmotionReport WHERE targetDate = :targetDate")
    fun getEmotionReportDetail(targetDate: String): Maybe<EmotionReport>

    @Query("SELECT * FROM EmotionReport WHERE targetDate BETWEEN :startDate AND :endDate")
    fun getDashboardPerWeek(startDate : String, endDate: String): Single<List<EmotionReport>>

    @Insert
    fun insertEmotionReports(emotionReports: List<EmotionReport>) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmotionReport(emotionReport: EmotionReport) : Completable

    @Query("DELETE FROM EmotionReport WHERE targetDate = :targetDate")
    fun deleteEmotionReport(targetDate : String) : Completable

    @Query("DELETE FROM EmotionReport")
    fun deleteAllEmotionReport() : Completable
}