package com.songjem.emotionnote.presentation.main.record

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.songjem.domain.model.DailyEmotion
import com.songjem.domain.model.EmotionReportItem
import com.songjem.domain.model.SentimentAnalysisItem
import com.songjem.domain.usecase.analysis.SentimentAnalysisUseCase
import com.songjem.domain.usecase.emotion.InsertEmotionReportUseCase
import com.songjem.domain.usecase.test.*
import com.songjem.emotionnote.base.BaseViewModel
import com.songjem.emotionnote.di.BaseApplication
import com.songjem.emotionnote.utils.def.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val sentimentAnalysisUseCase: SentimentAnalysisUseCase,
    private val insertEmotionReportUseCase: InsertEmotionReportUseCase
)
    : BaseViewModel() {

    private lateinit var speechRecognizer: SpeechRecognizer
    lateinit var intent: Intent
    lateinit var matches: ArrayList<String>

    private var _voiceRecordContent = MutableLiveData<String>()
    val voiceRecordContent : LiveData<String> get() = _voiceRecordContent

    private var _dailyEmotion = MutableLiveData<DailyEmotion>()
    val dailyEmotion : LiveData<DailyEmotion> get() = _dailyEmotion

    private var _insertReportResult = MutableLiveData<Boolean>()
    val insertReportResult : LiveData<Boolean> get() = _insertReportResult

    init {
        initVoiceRecord()
    }

    @SuppressLint("CheckResult")
    fun insertEmotionReport(emotionReport : EmotionReportItem) {
        insertEmotionReportUseCase(emotionReport)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("songjem", "insertEmotionReport")
                _insertReportResult.value = true
            }, {
                Log.d("songjem", "insertEmotionReport throwable, msg = " + it.message)
            })
    }

    @SuppressLint("CheckResult")
    fun reqSentimentAnalysis(dailyReport: String) {
        val clientId = Constants.NAVER_CLIENT_ID
        val clientSecret = Constants.NAVER_CLIENT_SECRET

        val sentimentAnalysisItem = SentimentAnalysisItem(clientId, clientSecret, SentimentAnalysisItem.SentimentData(dailyReport))
        sentimentAnalysisUseCase(sentimentAnalysisItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("songjem", "ReqSentimentAnalysis response, DailyEmotion = $it")
                _dailyEmotion.value = it
            }, {
                Log.e("songjem","ReqSentimentAnalysis error, msg = " + it.message)
            })
    }

    fun dailyVoiceRecord() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(BaseApplication.context())
        speechRecognizer.setRecognitionListener(listener())
        speechRecognizer.startListening(intent)
    }

    private fun listener(): RecognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {
            Toast.makeText(BaseApplication.context(), "onReadyForSpeech", Toast.LENGTH_SHORT).show()
        }

        override fun onRmsChanged(p0: Float) {
            Toast.makeText(BaseApplication.context(), "onRmsChanged", Toast.LENGTH_SHORT).show()
        }

        override fun onBufferReceived(p0: ByteArray?) {
            Toast.makeText(BaseApplication.context(), "onBufferReceived", Toast.LENGTH_SHORT).show()
        }

        override fun onPartialResults(p0: Bundle?) {
            Toast.makeText(BaseApplication.context(), "onPartialResults", Toast.LENGTH_SHORT).show()
        }

        override fun onEvent(p0: Int, p1: Bundle?) {
            Toast.makeText(BaseApplication.context(), "onEvent", Toast.LENGTH_SHORT).show()
        }

        override fun onBeginningOfSpeech() {
            Toast.makeText(BaseApplication.context(), "onBeginningOfSpeech", Toast.LENGTH_SHORT).show()
        }

        override fun onEndOfSpeech() {
            Toast.makeText(BaseApplication.context(), "onEndOfSpeech", Toast.LENGTH_SHORT).show()
        }

        override fun onError(error: Int) {
            val errorMsg = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 에러"
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "퍼미션 없음"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트워크 타임아웃"
                SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RECOGNIZER BUSY"
                SpeechRecognizer.ERROR_SERVER -> "서버 오류"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "녹음시간 초과"
                else -> "알 수 없는 오류"
            }
            Toast.makeText(BaseApplication.context(), "onError, errorMsg = $errorMsg", Toast.LENGTH_SHORT).show()
            Log.e("songjem", "errorMsg = $errorMsg")
        }

        override fun onResults(result: Bundle?) {
            Toast.makeText(BaseApplication.context(), "onResults", Toast.LENGTH_SHORT).show()
            matches = result!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) as ArrayList<String>

            val sb: StringBuilder = StringBuilder()
            for (i in 0 until matches.size) {
                sb.append(matches[i])
            }
            Log.d("songjem", "voice daily record data = $sb")
            _voiceRecordContent.value = sb.toString()
        }
    }

    private fun initVoiceRecord() {
        intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, BaseApplication.context().packageName)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
    }
}