package com.songjem.emotionnote.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseActivity
import com.songjem.emotionnote.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        setObserve()
    }

    private fun setObserve() {
        viewModel.testVal.observe(this@MainActivity, Observer { testData ->
            binding.tvTestResult.text = testData
        })
    }
}