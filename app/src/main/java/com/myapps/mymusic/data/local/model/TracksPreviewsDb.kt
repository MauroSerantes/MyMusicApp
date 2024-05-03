package com.myapps.mymusic.data.local.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myapps.mymusic.data.local.service.AlbumCacheDao
import com.myapps.mymusic.data.local.service.ArtistCacheDao
import com.myapps.mymusic.data.local.service.GenreCacheDao
import com.myapps.mymusic.data.local.service.PlaylistCacheDao
import com.myapps.mymusic.data.local.service.RadioCacheDao
import com.myapps.mymusic.data.local.service.TracksCacheDao
import com.myapps.mymusic.data.local.service.TracksDao

@Database(entities = [FavouriteTrack::class, TrackCache::class, AlbumCache::class, GenreCache::class, PlaylistCache::class, ArtistCache::class ,RadioCache::class], version = 13, exportSchema = true)
abstract class TracksPreviewsDb:RoomDatabase(){
    abstract fun tracksDao(): TracksDao
    abstract fun tracksCacheDao(): TracksCacheDao
    abstract fun artistsCacheDao():ArtistCacheDao
    abstract fun albumsCacheDao():AlbumCacheDao
    abstract fun playlistsCacheDao():PlaylistCacheDao
    abstract fun genresCacheDao():GenreCacheDao
    abstract fun radioCacheDao():RadioCacheDao
}