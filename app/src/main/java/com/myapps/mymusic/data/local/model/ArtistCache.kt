package com.myapps.mymusic.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapps.mymusic.data.local.utils.ArtistCacheConstants.ID
import com.myapps.mymusic.data.local.utils.ArtistCacheConstants.NAME
import com.myapps.mymusic.data.local.utils.ArtistCacheConstants.PICTURE
import com.myapps.mymusic.data.local.utils.ArtistCacheConstants.PICTURE_XL
import com.myapps.mymusic.data.local.utils.ArtistCacheConstants.TYPE_OF_CACHE
import com.myapps.mymusic.data.local.utils.DatabaseConstants.ARTIST_CACHE

@Entity(tableName =ARTIST_CACHE)
data class ArtistCache(
    @ColumnInfo(ID)
    @PrimaryKey(autoGenerate = false)
    val id:Long,
    @ColumnInfo(NAME)
    val name:String,
    @ColumnInfo(PICTURE)
    val picture:String,
    @ColumnInfo(PICTURE_XL)
    val pictureXl:String,
    @ColumnInfo(TYPE_OF_CACHE)
    val typeOfCache:Int
)
