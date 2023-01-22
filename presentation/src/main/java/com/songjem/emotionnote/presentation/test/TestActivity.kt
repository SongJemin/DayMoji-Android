package com.songjem.emotionnote.presentation.test

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseActivity
import com.songjem.emotionnote.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : BaseActivity<ActivityTestBinding>(R.layout.activity_test) {

    private val viewModel : TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        setObserve()
    }

    private fun setObserve() {
        viewModel.emotionReportListData.observe(this@TestActivity, Observer { testData ->
            binding.tvTestResult.text = testData
        })
    }
}