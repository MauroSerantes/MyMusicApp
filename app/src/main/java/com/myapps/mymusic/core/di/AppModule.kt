package com.myapps.mymusic.core.di

import com.myapps.mymusic.data.MusicRepository
import com.myapps.mymusic.data.local.LocalSource
import com.myapps.mymusic.data.remote.RemoteSource
import com.myapps.mymusic.data.utils.NetworkObserver
import com.myapps.mymusic.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRepository(remoteSource: RemoteSource, localSource: LocalSource,networkObserver: NetworkObserver): Repository {
        return MusicRepository(remoteSource,localSource,networkObserver)
    }
}