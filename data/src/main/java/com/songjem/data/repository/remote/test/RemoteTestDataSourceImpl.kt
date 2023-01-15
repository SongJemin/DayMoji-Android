package com.songjem.data.repository.remote.test

import com.songjem.data.datasource.remote.api.TestApi
import com.songjem.data.datasource.remote.response.TestRemoteResponse
import io.reactivex.Single
import javax.inject.Inject

class RemoteTestDataSourceImpl
    @Inject constructor(private val testApi: TestApi) : RemoteTestDataSource {
    override fun getRemoteAllTestData() : Single<TestRemoteResponse> {
        return testApi.getTestData()
    }
}