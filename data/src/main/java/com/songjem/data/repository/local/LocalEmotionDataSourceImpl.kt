package com.songjem.data.repository.local

import com.songjem.data.datasource.local.dao.EmotionDao
import com.songjem.data.datasource.local.model.EmotionReport
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class LocalEmotionDataSourceImpl @Inject constructor(private val emotionDao: EmotionDao)
    : LocalEmotionDataSource {

    override fun getAllEmotionReport(): Single<List<EmotionReport>> = emotionDao.getAllEmotionReport()

    override fun getEmotionReportMonthly(targetYearMonth: String): Single<List<EmotionReport>> = emotionDao.getEmotionReportMonthly(targetYearMonth)

    override fun getEmotionReportDetail(targetDate: String): Maybe<EmotionReport> = emotionDao.getEmotionReportDetail(targetDate)

    override fun insertEmotionReports(emotionReports: List<EmotionReport>): Completable = emotionDao.insertEmotionReports(emotionReports)

    override fun insertEmotionReport(emotionReport: EmotionReport): Completable = emotionDao.insertEmotionReport(emotionReport)

    override fun deleteAllEmotionReport(): Completable = emotionDao.deleteAllEmotionReport()
}