package com.myapps.mymusic.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapps.mymusic.data.local.utils.DatabaseConstants.RADIO_CACHE
import com.myapps.mymusic.data.local.utils.RadioCacheConstants.ID
import com.myapps.mymusic.data.local.utils.RadioCacheConstants.PICTURE
import com.myapps.mymusic.data.local.utils.RadioCacheConstants.PICTURE_XL
import com.myapps.mymusic.data.local.utils.RadioCacheConstants.TITLE

@Entity(tableName = RADIO_CACHE)
data class RadioCache(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(ID)
    val id:Long,
    @ColumnInfo(TITLE)
    val title:String,
    @ColumnInfo(PICTURE)
    val picture:String,
    @ColumnInfo(PICTURE_XL)
    val pictureXL:String
)
