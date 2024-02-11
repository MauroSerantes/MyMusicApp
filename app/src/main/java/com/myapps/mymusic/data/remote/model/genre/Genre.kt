package com.myapps.mymusic.data.remote.model.genre

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id:Long,
    @SerializedName("name")
    val name:String,
    @SerializedName("picture")
    val picture:String,
    @SerializedName("picture_big")
    val pictureBig:String,
)

