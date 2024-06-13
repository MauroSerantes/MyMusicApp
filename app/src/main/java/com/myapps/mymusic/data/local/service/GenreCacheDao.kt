package com.myapps.mymusic.data.local.service

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myapps.mymusic.data.local.model.GenreCache
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreCacheDao {
    @Upsert
    suspend fun upsertGenresCache(genresListCache:List<GenreCache>)

    @Query("DELETE FROM GenreCache")
    suspend fun deleteGenresCache()

    @Query("SELECT * FROM GenreCache ")
    fun getGenresCache(): Flow<List<GenreCache>>

    @Query("SELECT COUNT(*) FROM GenreCache")
    suspend fun genreCacheCount():Int
}