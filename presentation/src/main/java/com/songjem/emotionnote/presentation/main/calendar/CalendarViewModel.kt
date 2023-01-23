package com.songjem.emotionnote.presentation.main.calendar

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.songjem.domain.model.EmotionReportItem
import com.songjem.domain.usecase.emotion.DeleteAllEmotionReportUseCase
import com.songjem.domain.usecase.emotion.GetEmotionDetailUseCase
import com.songjem.domain.usecase.emotion.GetEmotionReportsUseCase
import com.songjem.emotionnote.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel
@Inject constructor(
    private val getEmotionReportsUseCase: GetEmotionReportsUseCase,
    private val getEmotionDetailUseCase : GetEmotionDetailUseCase
) : BaseViewModel() {

    private var _emotionReportListData = MutableLiveData<String>()
    val emotionReportListData : LiveData<String> get() = _emotionReportListData

    private var _emotionReport = MutableLiveData<EmotionReportItem>()
    val emotionReport : LiveData<EmotionReportItem> get() = _emotionReport

    init {
        getEmotionReportList()
        getEmotionDetail("20230123")
    }

    fun getEmotionDetail(targetDate : String) {
        getEmotionDetailUseCase(targetDate)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ data ->
                if (data != null) {
                    _emotionReport.value = data
                } else {
                    Log.d("songjem", "No Report Data")
                }
            }, {
                Log.e("songjem", "getEmotionDetail Error, msg = " + it.message)
            })
    }

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
}