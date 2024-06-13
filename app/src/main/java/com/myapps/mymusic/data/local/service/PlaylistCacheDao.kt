package com.myapps.mymusic.data.local.service

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myapps.mymusic.data.local.model.PlaylistCache
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistCacheDao {
    @Upsert
    suspend fun upsertPlaylistsCache(playlistListCache:List<PlaylistCache>)

    @Query("DELETE FROM PlaylistCache")
    suspend fun deletePlaylistsCache()

    @Query("SELECT * FROM PlaylistCache")
    fun getPlaylistsCache(): Flow<List<PlaylistCache>>

    @Query("SELECT COUNT(*) FROM PlaylistCache")
    suspend fun playlistCacheCount():Int
}