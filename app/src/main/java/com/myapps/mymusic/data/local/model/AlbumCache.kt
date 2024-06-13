package com.myapps.mymusic.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapps.mymusic.data.local.utils.DatabaseConstants.ALBUM_CACHE
import com.myapps.mymusic.data.local.utils.AlbumCacheConstants.ID
import com.myapps.mymusic.data.local.utils.AlbumCacheConstants.COVER
import com.myapps.mymusic.data.local.utils.AlbumCacheConstants.TITLE
import com.myapps.mymusic.data.local.utils.AlbumCacheConstants.COVER_XL
import com.myapps.mymusic.data.local.utils.AlbumCacheConstants.TYPE_OF_CACHE
import com.myapps.mymusic.data.model.AlbumDto

@Entity(tableName = ALBUM_CACHE)
data class AlbumCache(
    @ColumnInfo(ID)
    @PrimaryKey(autoGenerate = false)
    val id:Long,
    @ColumnInfo(TITLE)
    val title:String,
    @ColumnInfo(COVER)
    val cover:String,
    @ColumnInfo(COVER_XL)
    val coverXl:String,
    @ColumnInfo(TYPE_OF_CACHE)
    val typeOfCache:Int
)
