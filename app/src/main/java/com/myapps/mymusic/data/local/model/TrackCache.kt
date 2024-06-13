package com.myapps.mymusic.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapps.mymusic.data.local.utils.DatabaseConstants.TRACK_CACHE
import com.myapps.mymusic.data.local.utils.TrackCacheConstants.ALBUM_COVER
import com.myapps.mymusic.data.local.utils.TrackCacheConstants.ALBUM_NAME
import com.myapps.mymusic.data.local.utils.TrackCacheConstants.ARTIST_NAME
import com.myapps.mymusic.data.local.utils.TrackCacheConstants.ID
import com.myapps.mymusic.data.local.utils.TrackCacheConstants.PREVIEW
import com.myapps.mymusic.data.local.utils.TrackCacheConstants.TITLE
import com.myapps.mymusic.data.local.utils.TrackCacheConstants.TYPE_OF_CACHE

@Entity(tableName = TRACK_CACHE)
data class TrackCache(
    @ColumnInfo(ID)
    @PrimaryKey(autoGenerate = false)
    val id:Long,
    @ColumnInfo(TITLE)
    val title:String,
    @ColumnInfo(PREVIEW)
    val preview:String,
    @ColumnInfo(ARTIST_NAME)
    val artistName:String,
    @ColumnInfo(ALBUM_NAME)
    val albumName:String,
    @ColumnInfo(ALBUM_COVER)
    val albumCover:String,
    @ColumnInfo(TYPE_OF_CACHE)
    val typeOfCache:Int
)
