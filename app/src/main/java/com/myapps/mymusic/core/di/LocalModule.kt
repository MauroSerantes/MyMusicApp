package com.myapps.mymusic.core.di

import android.app.Application
import androidx.room.Room
import com.myapps.mymusic.ApplicationClass
import com.myapps.mymusic.data.local.LocalSource
import com.myapps.mymusic.data.local.model.TracksPreviewsDb
import com.myapps.mymusic.data.local.service.TracksDao
import com.myapps.mymusic.data.local.utils.DatabaseConstants.DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(app:Application): TracksPreviewsDb {
        return Room.databaseBuilder(app, TracksPreviewsDb::class.java, DATA_BASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteTracksDao(database: TracksPreviewsDb): TracksDao {
        return database.tracksDao()
    }

    @Provides
    @Singleton
    fun provideLocalSource(trackDao:TracksDao):LocalSource {
        return LocalSource(trackDao)
    }
}
