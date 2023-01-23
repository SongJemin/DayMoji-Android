package com.songjem.domain.repository

import com.songjem.domain.model.EmotionReportItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface EmotionRepository {
    fun getAllTestData(
    ) : Flowable<List<EmotionReportItem>>

    fun getAllEmotionReport(
    ) : Flowable<List<EmotionReportItem>>

    fun getEmotionReportDetail(
        targetDate : String
    ) : Maybe<EmotionReportItem>

    fun getRemoteTestDatas(
    ) : Single<List<EmotionReportItem>>

    fun insertLocalData(
        emotionReportItem: EmotionReportItem
    ) : Completable

    fun deleteAllLocalData() : Completable
}