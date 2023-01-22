package com.songjem.data.repository

import android.util.Log
import com.songjem.data.datasource.local.model.EmotionReport
import com.songjem.data.mapper.emotion.mapperToTest
import com.songjem.data.repository.local.LocalEmotionDataSource
import com.songjem.data.repository.remote.test.RemoteTestDataSource
import com.songjem.data.util.DateUtil
import com.songjem.data.util.DateUtil.dateToString
import com.songjem.domain.model.EmotionReportItem
import com.songjem.domain.repository.EmotionRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class EmotionRepositoryImpl @Inject constructor(
    private val localEmotionDataSource: LocalEmotionDataSource,
    private val remoteTestDataSource: RemoteTestDataSource
) : EmotionRepository {

    override fun getAllTestData(): Flowable<List<EmotionReportItem>> {
        return localEmotionDataSource.getAllEmotionReport()
            .onErrorReturn { listOf() }
            .flatMapPublisher { datas ->
                if(datas.isEmpty()) {
                    getRemoteTestDatas()
                        .toFlowable()
                        .onErrorReturn { listOf() }
                } else {
                    val localData = Single.just(mapperToTest(datas))
                    val remoteData = getRemoteTestDatas()
                        .onErrorResumeNext { localData }
                    Single.concat(localData, remoteData)
                }
            }
    }

    override fun getAllEmotionReport(): Flowable<List<EmotionReportItem>> {
        return localEmotionDataSource.getAllEmotionReport()
            .onErrorReturn { listOf() }
            .flatMapPublisher { localDatas ->
                Log.d("songjem", "localDatas = $localDatas")
                Flowable.just(mapperToTest(localDatas))
//                if(localDatas.isEmpty()) {
//                    Flowable.error(java.lang.IllegalStateException("LocalData is Empty"))
//                } else {
//                    Flowable.just(mapperToTest(localDatas))
//                }
            }
    }

    override fun getRemoteTestDatas(): Single<List<EmotionReportItem>> {
        return remoteTestDataSource.getRemoteAllTestData()
            .flatMap {
                localEmotionDataSource.insertEmotionReports(it.testDatas!!)
                    .andThen(Single.just(mapperToTest(it.testDatas)))
            }
    }

    override fun insertLocalData(emotionReportItem: EmotionReportItem): Completable {
        Log.d("songjem", "insertEmotionReport = $emotionReportItem")
        val currentDateTime = DateUtil.currentDate().dateToString("yyyy.MM.dd kk:mm:ss E", Locale("ko", "KR"))
        val emotionReport = EmotionReport(emotionReportItem.index, emotionReportItem.targetDate, emotionReportItem.reportContent
            , emotionReportItem.emotionStatus, emotionReportItem.positive, emotionReportItem.negative, emotionReportItem.neutral, null, null
            , currentDateTime, currentDateTime)
        return localEmotionDataSource.insertEmotionReport(emotionReport)
    }

    override fun deleteAllLocalData(): Completable {
        Log.d("songjem", "deleteAllEmotionReport")
        return localEmotionDataSource.deleteAllEmotionReport()
    }
}