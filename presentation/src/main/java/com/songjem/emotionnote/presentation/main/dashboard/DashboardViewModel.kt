package com.songjem.emotionnote.presentation.main.dashboard

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.songjem.data.util.DateUtil
import com.songjem.data.util.DateUtil.dateToString
import com.songjem.domain.model.DashBoardEmotionItem
import com.songjem.domain.usecase.emotion.GetDashboardPerWeekUseCase
import com.songjem.emotionnote.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
@Inject constructor(
    private val getDashboardPerWeekUseCase: GetDashboardPerWeekUseCase
): BaseViewModel() {

    private var _dashboardEmotions = MutableLiveData<List<DashBoardEmotionItem>>()
    val dashboardEmotions : LiveData<List<DashBoardEmotionItem>> get() = _dashboardEmotions

    private var _noDataAlarm = MutableLiveData<Boolean>()
    val noDataAlarm : LiveData<Boolean> get() = _noDataAlarm

    private var _errorAlarm = MutableLiveData<String>()
    val errorAlarm : LiveData<String> get() = _errorAlarm

    @SuppressLint("CheckResult")
    fun getDashboardPerWeek(startDate : String, endDate : String) {
        Log.d("songjem", "getDashboardPerWeek, startDate = $startDate, endDate = $endDate")

        getDashboardPerWeekUseCase(startDate, endDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ datas ->
                if(datas.isNotEmpty()) {
                    Log.d("songjem", "getDashboardPerWeek Success, datas = $datas")
                    _dashboardEmotions.value = datas
                } else {
                    Log.d("songjem", "getDashboardPerWeek No Data")
                }
            }, {
                _errorAlarm.value = it.message
            })
    }
}