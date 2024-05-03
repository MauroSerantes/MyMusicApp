package com.myapps.mymusic.data.model

import com.myapps.mymusic.data.local.model.ArtistCache

data class ArtistDto(
    val id:Long,
    val name:String,
    val picture:String,
    val pictureXL: String
)