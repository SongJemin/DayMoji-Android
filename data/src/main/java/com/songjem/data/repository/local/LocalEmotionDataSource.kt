package com.songjem.data.repository.local

import com.songjem.data.datasource.local.model.EmotionReport
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface LocalEmotionDataSource {
    fun getAllEmotionReport() : Single<List<EmotionReport>>
    fun getEmotionReportMonthly(targetYearMonth : String) : Single<List<EmotionReport>>
    fun getEmotionReportDetail(targetDate : String) : Maybe<EmotionReport>
    fun getDashboardPerPeriod(startDate : String, endDate: String) : Single<List<EmotionReport>>
    fun insertEmotionReports(emtionRepots: List<EmotionReport>): Completable
    fun insertEmotionReport(emotionReport: EmotionReport): Completable
    fun deleteEmotionReport(targetDate : String) : Completable
    fun deleteAllEmotionReport(): Completable
}