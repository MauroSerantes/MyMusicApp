package com.myapps.mymusic.data.local.service

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myapps.mymusic.data.local.model.FavouriteTrack
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao{

    @Upsert
    suspend fun upsertTrack(favTrack: FavouriteTrack)

    @Query("DELETE FROM Tracks WHERE id=:id")
    suspend fun deleteTrack(id:Long)

    @Query("SELECT COUNT (*) FROM Tracks WHERE id=:id")
    suspend fun favTrackCount(id: Long) : Int

    @Query("SELECT * FROM Tracks")
    fun getAllFavouriteTracks():Flow<List<FavouriteTrack>>
}