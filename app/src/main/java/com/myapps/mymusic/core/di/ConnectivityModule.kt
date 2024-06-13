package com.myapps.mymusic.core.di

import android.app.Application
import com.myapps.mymusic.data.utils.NetworkObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConnectivityModule {
    @Provides
    @Singleton
    fun providesNetworkObserver(app:Application):NetworkObserver{
        return NetworkObserver(app)
    }
}