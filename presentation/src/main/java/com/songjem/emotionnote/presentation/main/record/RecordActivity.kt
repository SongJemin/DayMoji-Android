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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecordActivity : BaseActivity<ActivityRecordBinding>(R.layout.activity_record) {
    private val viewModel : RecordViewModel by viewModels()
    private lateinit var targetDate : String
    private lateinit var addOrEdit : String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        checkPermission()
        targetDate = intent.getStringExtra("targetDate")!!
        addOrEdit = intent.getStringExtra("addOrEdit")!!
        Log.d("songjem", "RecordActivity, getIntent targetDate = $targetDate")
        Log.d("songjem", "RecordActivity, getIntent addOrEdit = $addOrEdit")
        binding.tvTargetDateRecord.text = targetDate.substring(0,4) + ". " + targetDate.substring(4,6) + ". " + targetDate.substring(6,8)

        if(addOrEdit == "EDIT") getDailyEmotionDetail(targetDate)
        setObserve()
        setButtonClick()
        setSwitch()
    }

    private fun setSwitch() {
        binding.swSecretRecord.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                binding.tvSecretRecord.text = "secret ON"
            } else {
                binding.tvSecretRecord.text = "secret OFF"
            }
        }
    }

    private fun getDailyEmotionDetail(targetDate : String) {
        viewModel.getEmotionDetail(targetDate)
    }

    @SuppressLint("SetTextI18n")
    private fun setObserve() {
        viewModel.dailyEmotion.observe(this) { dailyEmotion ->
            val emotionScore = (dailyEmotion.emotionDetail.positive - dailyEmotion.emotionDetail.negative) / 2
            val emotionStatus = if(emotionScore > 25.0) "행복함"
            else if(emotionScore in -10.0..10.0) "그저그럼"
            else if(emotionScore < 25.0 && emotionScore >= 0.0) "즐거움"
            else if(emotionScore < 0.0 && emotionScore >= -25.0) "슬픔"
            else if(emotionScore < -25.0 && emotionScore >= -50.0) "화남"
            else "그저그럼"

            binding.tvEmotionStatusResultRecord.text = emotionStatus
            binding.tvEmotionPositiveResultRecord.text = String.format("%.5f", dailyEmotion.emotionDetail.positive)
            binding.tvEmotionNegativeResultRecord.text = String.format("%.5f", dailyEmotion.emotionDetail.negative)
            binding.tvEmotionNeutralResultRecord.text = String.format("%.5f", dailyEmotion.emotionDetail.neutral)
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

        viewModel.emotionReport.observe(this) { report ->
            binding.etDailyEmotionRecord.setText(report.reportContent)
            binding.tvEmotionStatusResultRecord.text = report.emotionStatus
            binding.tvEmotionPositiveResultRecord.text = report.positive.toString()
            binding.tvEmotionNegativeResultRecord.text = report.negative.toString()
            binding.tvEmotionNeutralResultRecord.text = report.neutral.toString()
            binding.btnSaveAnalysisRecord.text = "수정하기"
        }
    }

    private fun setButtonClick() {
        binding.btnSaveAnalysisRecord.setOnClickListener {
            val currentDate = DateUtil.currentDate().dateToString("yyyyMMdd")
            val emotionStatus : String = binding.tvEmotionStatusResultRecord.text.toString()
            val reportContent : String? = binding.etDailyEmotionRecord.text?.toString()
            val positiveString : String = binding.tvEmotionPositiveResultRecord.text.toString()
            val negativeString : String = binding.tvEmotionNegativeResultRecord.text.toString()
            val neutralString : String = binding.tvEmotionNeutralResultRecord.text.toString()

            if(emotionStatus != "No Data" && reportContent != null && positiveString != "No Data" && negativeString != "No Data" && neutralString != "No Data") {
                val emotionReport = EmotionReportItem(targetDate = targetDate, reportContent = reportContent, emotionStatus = emotionStatus, positive = positiveString.toFloat(),
                    negative = negativeString.toFloat(), neutral = neutralString.toFloat(), firstReportDate = currentDate, lastReportDate = currentDate, isSecretMode = false)

                Log.d("songjem", "INSERT, emotionReportItem = $emotionReport")
                viewModel.insertEmotionReport(emotionReport)
            } else {
                Toast.makeText(applicationContext, "감정분석한 후에 저장해주세요", Toast.LENGTH_SHORT).show()
            }
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