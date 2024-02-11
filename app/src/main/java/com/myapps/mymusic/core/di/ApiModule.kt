package com.myapps.mymusic.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.myapps.mymusic.data.remote.RemoteSource
import com.myapps.mymusic.data.remote.service.DeezerApiService
import com.myapps.mymusic.core.di.ApiModuleConstants.BASE_URL
import com.myapps.mymusic.core.di.ApiModuleConstants.NETWORK_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    fun provideNetworkTome() = NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()


    @Provides
    @Singleton
    fun provideBodyInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideClient(
        time: Long, body: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(body)
        .connectTimeout(time, TimeUnit.SECONDS)
        .readTimeout(time, TimeUnit.SECONDS)
        .writeTimeout(time, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun provideDeezerService(baseUrl: String, client: OkHttpClient, gson: Gson): DeezerApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DeezerApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteSource(apiService: DeezerApiService): RemoteSource {
        return RemoteSource(apiService)
    }
}