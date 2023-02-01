package com.songjem.emotionnote.presentation.main.calendar

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.songjem.data.util.DateUtil
import com.songjem.data.util.DateUtil.dateToString
import com.songjem.domain.model.EmotionReportItem
import com.songjem.domain.usecase.emotion.*
import com.songjem.emotionnote.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel
@Inject constructor(
    private val getEmotionReportsUseCase: GetEmotionReportsUseCase,
    private val getEmotionDetailUseCase : GetEmotionDetailUseCase,
    private val getEmotionMonthlyUseCase: GetEmotionMonthlyUseCase,
    private val deleteEmotionReportUseCase : DeleteEmotionReportUseCase
) : BaseViewModel() {

    private var _emotionReportListData = MutableLiveData<String>()
    val emotionReportListData : LiveData<String> get() = _emotionReportListData

    private var _emotionMonthlyReport = MutableLiveData<List<EmotionReportItem>>()
    val emotionMonthlyReport : LiveData<List<EmotionReportItem>> get() = _emotionMonthlyReport

    private var _emotionReport = MutableLiveData<EmotionReportItem>()
    val emotionReport : LiveData<EmotionReportItem> get() = _emotionReport

    private var _noDataAlarm = MutableLiveData<Boolean>()
    val noDataAlarm : LiveData<Boolean> get() = _noDataAlarm

    private var _errorAlarm = MutableLiveData<String>()
    val errorAlarm : LiveData<String> get() = _errorAlarm

    private var _deleteDate = MutableLiveData<String>()
    val deleteDate : LiveData<String> get() = _deleteDate

    init {
//        getEmotionReportList()
        val currentDate = DateUtil.currentDate().dateToString("yyyyMMdd")
        getEmotionReportMonthly(currentDate.substring(0, 4))
        getEmotionDetail(currentDate)
    }

    @SuppressLint("CheckResult")
    fun deleteEmotionReport(targetDate: String) {
        Log.d("songjem", "deleteTargetDate = $targetDate")
        deleteEmotionReportUseCase(targetDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("songjem", "deleteEmotionReport")
                _deleteDate.value = targetDate
            }, {
                Log.d("songjem", "deleteEmotionReport throwable, msg = " + it.message)
            })
    }

    @SuppressLint("CheckResult")
    fun getEmotionDetail(targetDate : String) {
        getEmotionDetailUseCase(targetDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                Log.d("songjem", "getEmotionDetail Success, report = $data")
                _emotionReport.value = data
            }, {
                Log.e("songjem", "getEmotionDetail Error, msg = " + it.message)
                _errorAlarm.value = it.message
               }, {
                Log.d("songjem","getEmotionDetail Complete, No Data")
                _noDataAlarm.value = true
            })
    }

    @SuppressLint("CheckResult")
    fun getEmotionReportMonthly(targetYearMonth : String) {
        Log.d("songjem", "getEmotionReportMonth = $targetYearMonth")
        getEmotionMonthlyUseCase(targetYearMonth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ datas ->
                if(datas.isNotEmpty()) {
                    Log.d("songjem", "getEmotionMonthly Success, datas = $datas")
                    _emotionMonthlyReport.value = datas
                } else {
                    Log.d("songjem", "getEmotionMonthly No Data")
                }
            }, {
                _errorAlarm.value = it.message
            })
    }

    @SuppressLint("CheckResult")
    fun getEmotionReportList() {
        getEmotionReportsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ datas ->
                if (datas.isEmpty()) {
                    _noDataAlarm.value = true
                } else {
                    _emotionReportListData.value = datas.toString()
                }
            }, {
                _errorAlarm.value = it.message
            })
    }
}