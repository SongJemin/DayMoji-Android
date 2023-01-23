package com.songjem.data.datasource.remote.response

import com.songjem.data.datasource.local.model.EmotionReport

data class TestRemoteResponse (
    val resultCode : Int,
    val message : String?,
    val testDatas : List<EmotionReport>?
)