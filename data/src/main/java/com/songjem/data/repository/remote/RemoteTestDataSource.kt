package com.songjem.data.repository.remote

import com.songjem.data.datasource.remote.response.TestRemoteResponse
import io.reactivex.Single

interface RemoteTestDataSource {
    fun getRemoteAllTestData(
    ): Single<TestRemoteResponse>
}