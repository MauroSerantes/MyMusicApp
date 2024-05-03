package com.myapps.mymusic.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapps.mymusic.data.local.utils.DatabaseConstants.FAVOURITE_TRACKS
import com.myapps.mymusic.data.local.utils.FavouriteTracksConstants.ALBUM_COVER
import com.myapps.mymusic.data.local.utils.FavouriteTracksConstants.ALBUM_NAME
import com.myapps.mymusic.data.local.utils.FavouriteTracksConstants.ARTIST_NAME
import com.myapps.mymusic.data.local.utils.FavouriteTracksConstants.ID
import com.myapps.mymusic.data.local.utils.FavouriteTracksConstants.PREVIEW
import com.myapps.mymusic.data.local.utils.FavouriteTracksConstants.TITLE

@Entity(tableName = FAVOURITE_TRACKS)
data class FavouriteTrack(
    @ColumnInfo(ID)
    @PrimaryKey(autoGenerate = false)
    val trackId:Long,
    @ColumnInfo(TITLE)
    val title:String,
    @ColumnInfo(PREVIEW)
    val preview:String,
    @ColumnInfo(ARTIST_NAME)
    val artistName:String,
    @ColumnInfo(ALBUM_NAME)
    val albumName:String,
    @ColumnInfo(ALBUM_COVER)
    val albumCover:String
)
