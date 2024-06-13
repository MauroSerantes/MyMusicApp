package com.myapps.mymusic.data.local.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.myapps.mymusic.data.local.model.AlbumCache
import com.myapps.mymusic.data.local.model.ArtistCache
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistCacheDao{

    @Upsert
    suspend fun upsertArtistsCache(artistListCache:List<ArtistCache>)

    @Query("DELETE FROM ArtistCache WHERE typeOfCache=:typeOfCache")
    suspend fun deleteArtistsCache(typeOfCache:Int)

    @Query("DELETE FROM ArtistCache WHERE typeOfCache=:typeOfCache AND id=:id ")
    suspend fun deleteArtistCache(id:Long,typeOfCache: Int)

    @Query("SELECT * FROM ArtistCache WHERE typeOfCache=:typeOfCache")
    fun getArtistsCache(typeOfCache: Int): Flow<List<ArtistCache>>

    @Query("SELECT COUNT(*) FROM ArtistCache WHERE typeOfCache=:typeOfCache")
    suspend fun artistCacheCount(typeOfCache: Int):Int

}