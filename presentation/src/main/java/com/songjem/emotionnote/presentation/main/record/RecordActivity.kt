package com.songjem.emotionnote.presentation.main.record

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.songjem.data.util.DateUtil
import com.songjem.data.util.DateUtil.dateToString
import com.songjem.domain.model.EmotionReportItem
import com.songjem.emotionnote.R
import com.songjem.emotionnote.base.BaseActivity
import com.songjem.emotionnote.databinding.ActivityRecordBinding
import com.songjem.emotionnote.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecordActivity : BaseActivity<ActivityRecordBinding>(R.layout.activity_record) {
    private val viewModel : RecordViewModel by viewModels()
    private lateinit var targetDate : String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        checkPermission()
        targetDate = intent.getStringExtra("targetDate")!!
        Log.d("songjem", "targetDate = $targetDate")
        binding.tvTargetDateCalendar.text = targetDate.substring(0,4) + ". " + targetDate.substring(4,6) + ". " + targetDate.substring(6,8)

        setObserve()
        setButtonClick()
    }

    @SuppressLint("SetTextI18n")
    private fun setObserve() {
        viewModel.dailyEmotion.observe(this) { dailyEmotion ->
            val emotionScore = (dailyEmotion.emotionDetail.positive - dailyEmotion.emotionDetail.negative) / 2
            val emotionStatus = if(emotionScore > 25.0) "행복함"
            else if(emotionScore in -10.0..10.0 && dailyEmotion.emotionDetail.neutral >= 50.0) "그저그럼"
            else if(emotionScore < 25.0 && emotionScore >= 0.0) "즐거움"
            else if(emotionScore < 0.0 && emotionScore >= -25.0) "슬픔"
            else if(emotionScore < -25.0 && emotionScore >= -50.0) "화남"
            else "그저그럼"

            binding.tvEmotionStatusResultRecord.text = emotionStatus
            binding.tvEmotionPositiveResultRecord.text = dailyEmotion.emotionDetail.positive.toString()
            binding.tvEmotionNegativeResultRecord.text = dailyEmotion.emotionDetail.negative.toString()
            binding.tvEmotionNeutralResultRecord.text = dailyEmotion.emotionDetail.neutral.toString()
            binding.tvCurrentDateResultRecord.text = DateUtil.currentDate().dateToString("yyyy. MM. dd")
        }

        viewModel.voiceRecordContent.observe(this) { recordContent ->
            binding.etDailyEmotionRecord.setText(recordContent)
        }

        viewModel.insertReportResult.observe(this) { insertResult ->
            Log.d("songjem", "insertResult = $insertResult")
            Toast.makeText(this, "분석된 감정이 등록이 되었습니다", Toast.LENGTH_SHORT).show()
            val intent = Intent()
            intent.putExtra("selectedDate", targetDate)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun setButtonClick() {
        binding.btnSaveAnalysisRecord.setOnClickListener {
            val currentDate = DateUtil.currentDate().dateToString("yyyyMMdd")
//            val currentDate = binding.tvCurrentDateResultRecord.text.toString().replace(" ", "").replace(".", "")
            val emotionStatus = binding.tvEmotionStatusResultRecord.text.toString()
            val reportContent = binding.etDailyEmotionRecord.text.toString()
            val positive = binding.tvEmotionPositiveResultRecord.text.toString().toFloat()
            val negative = binding.tvEmotionNegativeResultRecord.text.toString().toFloat()
            val neutral = binding.tvEmotionNeutralResultRecord.text.toString().toFloat()

            val emotionReport = EmotionReportItem(targetDate = targetDate, reportContent = reportContent
            , emotionStatus = emotionStatus, positive = positive, negative = negative
            , neutral = neutral, firstReportDate = currentDate, lastReportDate = currentDate)

            Log.d("songjem", "INSERT, emotionReportItem = $emotionReport")
            viewModel.insertEmotionReport(emotionReport)
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