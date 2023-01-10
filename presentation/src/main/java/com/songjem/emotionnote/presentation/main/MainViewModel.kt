package com.songjem.emotionnote.presentation.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.songjem.domain.model.TestItem
import com.songjem.domain.usecase.TestGetDataUseCase
import com.songjem.domain.usecase.TestGetLocalDataUseCase
import com.songjem.domain.usecase.TestGetRemoteDataUseCase
import com.songjem.domain.usecase.TestInsertDataUseCase
import com.songjem.emotionnote.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val testGetDataUseCase : TestGetDataUseCase,
    private val testGetLocalDataUseCase: TestGetLocalDataUseCase,
    private val testGetRemoteDataUseCase: TestGetRemoteDataUseCase,
    private val testInsertDataUseCase: TestInsertDataUseCase
) : BaseViewModel() {

    private var _testVal = MutableLiveData<String>()
    val testVal : LiveData<String> get() = _testVal

    fun insertTestData(testData : String) {
        val count : Int = 0
        val testItem = TestItem(count, testData)
        testInsertDataUseCase(testItem)
    }

    @SuppressLint("CheckResult")
    fun getTestLocalDatas() {
        testGetLocalDataUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ testDatas -> // currentQuery 를 사용하여 검색한 결과 값이 movie 에 들어있다.
                if (testDatas.isEmpty()) {
                    _testVal.value = "No Data"
                } else {
                    _testVal.value = testDatas.toString()
                }
            }, {
                _testVal.value = "Error"
            })
    }
}