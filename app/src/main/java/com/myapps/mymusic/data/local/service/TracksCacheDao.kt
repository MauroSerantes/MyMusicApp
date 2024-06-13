package com.myapps.mymusic.data.local.service

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myapps.mymusic.data.local.model.TrackCache
import com.myapps.mymusic.data.local.utils.TrackCacheConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksCacheDao {
    @Upsert
    suspend fun upsertTracksCache(trackListCache:List<TrackCache>)

    @Query("DELETE FROM TrackCache WHERE typeOfCache=:typeOfCache")
    suspend fun deleteTracksCache(typeOfCache:Int)

    @Query("DELETE FROM TrackCache WHERE typeOfCache=:typeOfCache AND id=:id ")
    suspend fun deleteTrackCache(id:Long,typeOfCache: Int)

    @Query("SELECT * FROM TrackCache WHERE typeOfCache=:typeOfCache")
    fun getTrackCache(typeOfCache: Int):Flow<List<TrackCache>>

    @Query("SELECT COUNT(*) FROM TrackCache WHERE typeOfCache=:typeOfCache")
    suspend fun trackCacheCount(typeOfCache: Int):Int
}