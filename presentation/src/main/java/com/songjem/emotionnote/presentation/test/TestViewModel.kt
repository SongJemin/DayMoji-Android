package com.songjem.emotionnote.presentation.test

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.songjem.domain.usecase.*
import com.songjem.domain.usecase.test.*
import com.songjem.emotionnote.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val testGetDataUseCase : TestGetDataUseCase,
    private val getEmotionReportsUseCase: GetEmotionReportsUseCase,
    private val testGetRemoteDataUseCase: TestGetRemoteDataUseCase,
    private val deleteAllEmotionReportUseCase : DeleteAllEmotionReportUseCase
    ) : BaseViewModel() {

    private var _emotionReportListData = MutableLiveData<String>()
    val emotionReportListData : LiveData<String> get() = _emotionReportListData

    @SuppressLint("CheckResult")
    fun getEmotionReportList() {
        getEmotionReportsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ datas ->
                if (datas.isEmpty()) {
                    _emotionReportListData.value = "No Data"
                } else {
                    _emotionReportListData.value = datas.toString()
                }
            }, {
                _emotionReportListData.value = "Error"
            })
    }

    @SuppressLint("CheckResult")
    fun deleteAllEmotionReport() {
        deleteAllEmotionReportUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("songjem", "deleteAllEmotionReport")
            }, {
                Log.d("songjem", "deleteAllEmotionReport, throwable msg = " + it.message)
            })
    }
}