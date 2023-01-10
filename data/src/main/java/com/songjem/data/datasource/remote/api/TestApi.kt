package com.songjem.data.datasource.remote.api

import com.songjem.data.datasource.remote.response.TestRemoteResponse
import io.reactivex.Single
import retrofit2.http.GET

interface TestApi {
    @GET("")
    fun getTestData(
    ) : Single<TestRemoteResponse>
}