package com.myapps.mymusic.core.di

import android.app.Application
import androidx.room.Room
import com.myapps.mymusic.data.local.LocalSource
import com.myapps.mymusic.data.local.model.TracksPreviewsDb
import com.myapps.mymusic.data.local.service.AlbumCacheDao
import com.myapps.mymusic.data.local.service.ArtistCacheDao
import com.myapps.mymusic.data.local.service.GenreCacheDao
import com.myapps.mymusic.data.local.service.PlaylistCacheDao
import com.myapps.mymusic.data.local.service.RadioCacheDao
import com.myapps.mymusic.data.local.service.TracksCacheDao
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
    fun providesTracksCacheDao(database:TracksPreviewsDb): TracksCacheDao {
        return database.tracksCacheDao()
    }

    @Provides
    @Singleton
    fun providesAlbumsCacheDao(database: TracksPreviewsDb):AlbumCacheDao{
        return database.albumsCacheDao()
    }

    @Provides
    @Singleton
    fun providesArtistCacheDao(database: TracksPreviewsDb):ArtistCacheDao{
        return database.artistsCacheDao()
    }
    @Provides
    @Singleton
    fun providesPlaylistsCacheDao(database: TracksPreviewsDb):PlaylistCacheDao{
        return database.playlistsCacheDao()
    }
    @Provides
    @Singleton
    fun providesGenresCacheDao(database: TracksPreviewsDb):GenreCacheDao{
        return database.genresCacheDao()
    }

    @Provides
    @Singleton
    fun providesRadioCacheDao(database: TracksPreviewsDb):RadioCacheDao{
        return database.radioCacheDao()
    }

    @Provides
    @Singleton
    fun provideLocalSource(
        trackDao:TracksDao,
        tracksCacheDao: TracksCacheDao,
        albumCacheDao: AlbumCacheDao,
        artistCacheDao: ArtistCacheDao,
        playlistCacheDao: PlaylistCacheDao,
        genreCacheDao: GenreCacheDao,
        radioCacheDao: RadioCacheDao
    ):LocalSource {
        return LocalSource(
            trackDao,
            tracksCacheDao,
            albumCacheDao,
            artistCacheDao,
            playlistCacheDao,
            genreCacheDao,
            radioCacheDao
        )
    }
}
