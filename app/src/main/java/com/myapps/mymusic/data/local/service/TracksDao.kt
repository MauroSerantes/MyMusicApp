package com.myapps.mymusic.data.local.service

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myapps.mymusic.data.local.model.FavouriteTrack
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_ALBUM_COVER
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_ALBUM_NAME
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_ARTIST_NAME
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_PREVIEW
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_TITLE
import com.myapps.mymusic.data.local.utils.DatabaseConstants.DATA_BASE_NAME
import com.myapps.mymusic.data.local.utils.DatabaseConstants.DATA_BASE_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao{

    @Upsert
    suspend fun upsertTrack(favTrack: FavouriteTrack)

    @Query("DELETE FROM Tracks WHERE " +
            "(title  = :title) AND" +
            "(preview = :preview) AND" +
            "(artistName = :artistName) AND" +
            "(albumName = :albumName) AND" +
            "(albumCover = :albumCover)"
    )
    suspend fun deleteTrack(
        title:String,
        preview:String,
        artistName:String,
        albumName:String,
        albumCover:String,
    )

    @Query("SELECT COUNT (*) FROM Tracks WHERE " +
            "(title = :title) AND " +
            "(albumName = :albumName) AND " +
            "(albumCover = :albumCover) AND " +
            "(preview = :preview) AND " +
            "(artistName = :artistName)"
    )
    suspend fun favTrackCount(
        title:String,
        preview:String,
        artistName:String,
        albumName:String,
        albumCover:String,
    ) : Int

    @Query("SELECT * FROM Tracks")
    fun getAllFavouriteTracks():Flow<List<FavouriteTrack>>
}