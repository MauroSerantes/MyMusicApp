package com.myapps.mymusic.data.model

import com.myapps.mymusic.data.local.model.TrackCache

data class TrackDto(
    val id:Long,
    val title:String,
    val preview:String,
    val artistName:String,
    var albumName:String,
    var albumCover:String,
)
