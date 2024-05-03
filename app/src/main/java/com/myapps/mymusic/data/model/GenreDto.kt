package com.myapps.mymusic.data.model

import com.myapps.mymusic.data.local.model.GenreCache

data class GenreDto(
    val id:Long,
    val name:String,
    val picture: String,
    val pictureBig: String
)