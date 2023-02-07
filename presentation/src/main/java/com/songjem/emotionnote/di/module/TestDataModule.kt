package com.songjem.emotionnote.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.songjem.data.datasource.local.dao.EmotionDao
import com.songjem.data.datasource.local.database.EmotionDatabase
import com.songjem.data.datasource.remote.api.NaverApi
import com.songjem.data.datasource.remote.api.TestApi
import com.songjem.data.repository.AnalysisRepositoryImpl
import com.songjem.data.repository.EmotionRepositoryImpl
import com.songjem.data.repository.local.LocalEmotionDataSource
import com.songjem.data.repository.local.LocalEmotionDataSourceImpl
import com.songjem.data.repository.remote.naver.AnalysisDataSource
import com.songjem.data.repository.remote.naver.AnalysisDataSourceImpl
import com.songjem.data.repository.remote.test.RemoteTestDataSource
import com.songjem.data.repository.remote.test.RemoteTestDataSourceImpl
import com.songjem.domain.repository.AnalysisRepository
import com.songjem.domain.repository.EmotionRepository
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
    fun provideEmotionDao(emotionDatabase: EmotionDatabase): EmotionDao {
        return emotionDatabase.emotionDao()
    }

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): EmotionDatabase {
        return Room.databaseBuilder(
            context,
            EmotionDatabase::class.java,
            "EmotionDiary.db"
        ).addMigrations(MIGRATION_1_2).build()
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE 'EmotionReport' ADD COLUMN 'isSecretMode' INTEGER NOT NULL DEFAULT 0")
        }
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(testApi: TestApi): RemoteTestDataSource {
        return RemoteTestDataSourceImpl(testApi)
    }

    @Singleton
    @Provides
    fun provideLocalEmotionDataSource(emotionDao: EmotionDao): LocalEmotionDataSource {
        return LocalEmotionDataSourceImpl(emotionDao)
    }

    @Singleton
    @Provides
    fun provideEmotionRepository(
        localEmotionDataSource: LocalEmotionDataSource,
        remoteTestDataSource: RemoteTestDataSource
    ): EmotionRepository {
        return EmotionRepositoryImpl(localEmotionDataSource, remoteTestDataSource)
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