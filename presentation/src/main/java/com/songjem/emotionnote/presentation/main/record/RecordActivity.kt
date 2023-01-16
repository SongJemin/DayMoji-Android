package com.songjem.emotionnote.presentation.main.record

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.songjem.data.util.DateUtil
import com.songjem.data.util.DateUtil.dateToString
import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseActivity
import com.songjem.emotionnote.databinding.ActivityRecordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordActivity : BaseActivity<ActivityRecordBinding>(R.layout.activity_record) {
    private val viewModel : RecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        checkPermission()
        setObserve()
    }

    private fun setObserve() {
        viewModel.dailyEmotion.observe(this) { dailyEmotion ->
            binding.tvEmotionStatusRecord.text = "오늘기분 : " + dailyEmotion.emotionStatus
            binding.tvEmotionPositiveLevelRecord.text = "긍정수치 : " + dailyEmotion.emotionDetail.positive.toString()
            binding.tvEmotionNegativeLevelRecord.text = "부정수치 : " + dailyEmotion.emotionDetail.negative.toString()
            binding.tvEmotionNeutralLevelRecord.text = "중립수치 : " + dailyEmotion.emotionDetail.neutral.toString()
            binding.tvCurrentDateRecord.text = "오늘날짜 : " + DateUtil.currentDate().dateToString("yyyyMMdd")
        }

        viewModel.voiceRecordContent.observe(this) { recordContent ->
            binding.etDailyEmotionRecord.setText(recordContent)
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