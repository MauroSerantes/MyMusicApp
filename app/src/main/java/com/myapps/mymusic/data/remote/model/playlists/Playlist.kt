package com.myapps.mymusic.data.remote.model.playlists

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("id")
    val id:Long,
    @SerializedName("title")
    val title:String,
    @SerializedName("picture")
    val picture:String,
    @SerializedName("picture_xl")
    val pictureXL:String,
)