package com.songjem.emotionnote.presentation.main.calendar

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.songjem.domain.usecase.test.DeleteAllEmotionReportUseCase
import com.songjem.domain.usecase.test.GetEmotionReportsUseCase
import com.songjem.emotionnote.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel
@Inject constructor(
    private val getEmotionReportsUseCase: GetEmotionReportsUseCase,
    private val deleteAllEmotionReportUseCase : DeleteAllEmotionReportUseCase
) : BaseViewModel() {

    private var _emotionReportListData = MutableLiveData<String>()
    val emotionReportListData : LiveData<String> get() = _emotionReportListData

    init {
        getEmotionReportList()
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