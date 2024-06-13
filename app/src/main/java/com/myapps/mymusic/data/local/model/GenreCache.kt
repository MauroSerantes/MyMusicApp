package com.myapps.mymusic.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapps.mymusic.data.local.utils.DatabaseConstants.GENRE_CACHE
import com.myapps.mymusic.data.local.utils.GenreCacheConstants.ID
import com.myapps.mymusic.data.local.utils.GenreCacheConstants.NAME
import com.myapps.mymusic.data.local.utils.GenreCacheConstants.PICTURE
import com.myapps.mymusic.data.local.utils.GenreCacheConstants.PICTURE_BIG
import com.myapps.mymusic.data.model.GenreDto


@Entity(tableName = GENRE_CACHE)
data class GenreCache(
    @ColumnInfo(ID)
    @PrimaryKey(autoGenerate = false)
    val id:Long,
    @ColumnInfo(NAME)
    val name:String,
    @ColumnInfo(PICTURE)
    val picture:String,
    @ColumnInfo(PICTURE_BIG)
    val pictureBig:String
)


