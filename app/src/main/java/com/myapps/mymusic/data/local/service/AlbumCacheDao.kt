package com.myapps.mymusic.data.local.service

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myapps.mymusic.data.local.model.AlbumCache
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumCacheDao {
    @Upsert
    suspend fun upsertAlbumsCache(albumListCache:List<AlbumCache>)

    @Query("DELETE FROM AlbumCache WHERE typeOfCache=:typeOfCache")
    suspend fun deleteAlbumsCache(typeOfCache: Int)

    @Query("DELETE FROM AlbumCache WHERE typeOfCache=:typeOfCache AND id=:id ")
    suspend fun deleteAlbumCache(id:Long,typeOfCache: Int)

    @Query("SELECT * FROM AlbumCache WHERE typeOfCache=:typeOfCache")
    fun getAlbumsCache(typeOfCache:Int): Flow<List<AlbumCache>>

    @Query("SELECT COUNT(*) FROM AlbumCache WHERE typeOfCache=:typeOfCache")
    suspend fun albumCacheCount(typeOfCache:Int):Int
}