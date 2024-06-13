package com.myapps.mymusic.data.local.service

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myapps.mymusic.data.local.model.PlaylistCache
import com.myapps.mymusic.data.local.model.RadioCache
import kotlinx.coroutines.flow.Flow


@Dao
interface RadioCacheDao {
    @Upsert
    suspend fun upsertRadioCache(radioListCache:List<RadioCache>)

    @Query("DELETE FROM RadioCache")
    suspend fun deleteRadioCache()

    @Query("SELECT * FROM RadioCache")
    fun getRadioCache(): Flow<List<RadioCache>>

    @Query("SELECT COUNT(*) FROM RadioCache")
    suspend fun radioCacheCount():Int
}