package com.songjem.data.datasource.remote.response

import com.songjem.data.datasource.local.model.TestEntity

data class TestRemoteResponse (
    val resultCode : Int,
    val message : String?,
    val testDatas : List<TestEntity>?
)