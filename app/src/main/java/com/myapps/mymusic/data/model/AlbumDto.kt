package com.myapps.mymusic.data.model

import com.myapps.mymusic.data.local.model.AlbumCache

data class AlbumDto(
    val id:Long,
    val title:String,
    val cover: String,
    val coverXL: String,
)