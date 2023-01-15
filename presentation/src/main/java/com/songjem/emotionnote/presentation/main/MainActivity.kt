package com.songjem.emotionnote.presentation.main

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.songjem.data.util.DateUtil
import com.songjem.data.util.DateUtil.dateToString
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
        checkPermission()
        setObserve()

        binding.btnWriteEmotionMain.setOnClickListener {
            binding.tvDailyEmotionMain.text = binding.etWriteEmotionMain.text.toString()
        }
    }

    private fun setObserve() {
        viewModel.dailyEmotion.observe(this) { dailyEmotion ->
            binding.tvEmotionStatusMain.text = "오늘기분 : " + dailyEmotion.emotionStatus
            binding.tvEmotionPositiveLevelMain.text = "긍정수치 : " + dailyEmotion.emotionDetail.positive.toString()
            binding.tvEmotionNegativeLevelMain.text = "부정수치 : " + dailyEmotion.emotionDetail.negative.toString()
            binding.tvEmotionNeutralLevelMain.text = "중립수치 : " + dailyEmotion.emotionDetail.neutral.toString()
            binding.tvCurrentDateMain.text = "오늘날짜 : " + DateUtil.currentDate().dateToString("yyyyMMdd")
        }

        viewModel.voiceRecordContent.observe(this) { recordContent ->
            binding.tvDailyEmotionMain.text = recordContent
        }
    }

    private fun checkPermission(){
        // 퍼미션 체크
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.RECORD_AUDIO
            ), 1
        )
    }
}