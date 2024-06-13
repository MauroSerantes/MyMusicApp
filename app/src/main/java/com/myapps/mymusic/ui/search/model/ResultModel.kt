package com.myapps.mymusic.ui.search.model

data class ResultModel(
    val id:Long,
    val title:String,
    val artistName:String = "",
    val albumName:String = "",
    val picture:String = "",
    val pictureXL:String,
    var preview:String = "",
    val type:String
)
