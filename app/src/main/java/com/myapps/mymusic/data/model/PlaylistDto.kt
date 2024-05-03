package com.myapps.mymusic.data.model

import com.myapps.mymusic.data.local.model.PlaylistCache

data class PlaylistDto(
    val id:Long,
    val title:String,
    val picture: String,
    val pictureXL: String,
)
