package com.myapps.mymusic.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapps.mymusic.data.local.utils.DatabaseConstants.PLAYLIST_CACHE
import com.myapps.mymusic.data.local.utils.PlaylistCacheConstants.ID
import com.myapps.mymusic.data.local.utils.PlaylistCacheConstants.PICTURE
import com.myapps.mymusic.data.local.utils.PlaylistCacheConstants.PICTURE_XL
import com.myapps.mymusic.data.local.utils.PlaylistCacheConstants.TITLE
import com.myapps.mymusic.data.model.PlaylistDto

@Entity(tableName = PLAYLIST_CACHE)
data class PlaylistCache(
    @ColumnInfo(ID)
    @PrimaryKey(autoGenerate = false)
    val id:Long,
    @ColumnInfo(TITLE)
    val title:String,
    @ColumnInfo(PICTURE)
    val picture:String,
    @ColumnInfo(PICTURE_XL)
    val pictureXl:String
)

