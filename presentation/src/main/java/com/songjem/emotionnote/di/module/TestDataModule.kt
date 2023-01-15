package com.songjem.emotionnote.di.module

import android.content.Context
import androidx.room.Room
import com.songjem.data.datasource.local.dao.TestDao
import com.songjem.data.datasource.local.database.TestDatabase
import com.songjem.data.datasource.remote.api.NaverApi
import com.songjem.data.datasource.remote.api.TestApi
import com.songjem.data.repository.AnalysisRepositoryImpl
import com.songjem.data.repository.TestRepositoryImpl
import com.songjem.data.repository.local.LocalTestDataSource
import com.songjem.data.repository.local.LocalTestDataSourceImpl
import com.songjem.data.repository.remote.naver.AnalysisDataSource
import com.songjem.data.repository.remote.naver.AnalysisDataSourceImpl
import com.songjem.data.repository.remote.test.RemoteTestDataSource
import com.songjem.data.repository.remote.test.RemoteTestDataSourceImpl
import com.songjem.domain.repository.AnalysisRepository
import com.songjem.domain.repository.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestDataModule {

    @Singleton
    @Provides
    fun provideTestDao(testDatabase: TestDatabase): TestDao {
        return testDatabase.testDao()
    }

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): TestDatabase {
        return Room.databaseBuilder(
            context,
            TestDatabase::class.java,
            "Test.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(testApi: TestApi): RemoteTestDataSource {
        return RemoteTestDataSourceImpl(testApi)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(testDao: TestDao): LocalTestDataSource {
        return LocalTestDataSourceImpl(testDao)
    }

    @Singleton
    @Provides
    fun provideTestRepository(
        localTestDataSource: LocalTestDataSource,
        remoteTestDataSource: RemoteTestDataSource
    ): TestRepository {
        return TestRepositoryImpl(localTestDataSource, remoteTestDataSource)
    }

    @Singleton
    @Provides
    fun provideAnalysisDataSource(naverApi: NaverApi) : AnalysisDataSource {
        return AnalysisDataSourceImpl(naverApi)
    }

    @Singleton
    @Provides
    fun provideAnalysisRepository(
        analysisDataSource : AnalysisDataSource,
    ): AnalysisRepository {
        return AnalysisRepositoryImpl(analysisDataSource)
    }
}