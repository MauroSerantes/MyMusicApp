package com.myapps.mymusic.data.model

data class TrackDto(
    val title:String,
    val preview:String,
    val artistName:String,
    var albumName:String,
    var albumCover:String,
)
