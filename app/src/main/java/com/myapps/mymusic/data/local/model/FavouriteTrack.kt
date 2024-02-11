package com.myapps.mymusic.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_ALBUM_COVER
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_ALBUM_NAME
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_ARTIST_NAME
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_ID
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_PREVIEW
import com.myapps.mymusic.data.local.utils.DatabaseConstants.COLUMN_NAME_TITLE
import com.myapps.mymusic.data.local.utils.DatabaseConstants.DATA_BASE_NAME
import com.myapps.mymusic.data.local.utils.DatabaseConstants.DATA_BASE_TABLE_NAME

@Entity(tableName = DATA_BASE_TABLE_NAME)
data class FavouriteTrack(
    @ColumnInfo(COLUMN_NAME_ID)
    @PrimaryKey(autoGenerate = true)
    val trackId:Long = 0,
    @ColumnInfo(COLUMN_NAME_TITLE)
    val title:String,
    @ColumnInfo(COLUMN_NAME_PREVIEW)
    val preview:String,
    @ColumnInfo(COLUMN_NAME_ARTIST_NAME)
    val artistName:String,
    @ColumnInfo(COLUMN_NAME_ALBUM_NAME)
    val albumName:String,
    @ColumnInfo(COLUMN_NAME_ALBUM_COVER)
    val albumCover:String
)
