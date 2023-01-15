package com.songjem.emotionnote.presentation.test

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.songjem.domain.model.TestItem
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
    private val testGetLocalDataUseCase: TestGetLocalDataUseCase,
    private val testGetRemoteDataUseCase: TestGetRemoteDataUseCase,
    private val testInsertDataUseCase: TestInsertDataUseCase,
    private val testDeleteAllDataUseCase : TestDeleteAllDataUseCase
    ) : BaseViewModel() {

    private var _testVal = MutableLiveData<String>()
    val testVal : LiveData<String> get() = _testVal

    @SuppressLint("CheckResult")
    fun insertTestData(testData : String) {
        val testItem = TestItem(testVal = testData)
        testInsertDataUseCase(testItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("songjem", "insertData")
            }, {
                Log.d("songjem", "insertData throwable, msg = " + it.message)
            })
    }

    @SuppressLint("CheckResult")
    fun getTestLocalDatas() {
        testGetLocalDataUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ testDatas ->
                if (testDatas.isEmpty()) {
                    _testVal.value = "No Data"
                } else {
                    _testVal.value = testDatas.toString()
                }
            }, {
                _testVal.value = "Error"
            })
    }

    @SuppressLint("CheckResult")
    fun deleteAllTestData() {
        testDeleteAllDataUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("songjem", "deleteAllTestData")
            }, {
                Log.d("songjem", "delete throwable msg = " + it.message)
            })
    }
}