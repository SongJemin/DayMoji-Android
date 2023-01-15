package com.songjem.data.repository.remote.test

import com.songjem.data.datasource.remote.response.TestRemoteResponse
import io.reactivex.Single

interface RemoteTestDataSource {
    fun getRemoteAllTestData(
    ): Single<TestRemoteResponse>
}