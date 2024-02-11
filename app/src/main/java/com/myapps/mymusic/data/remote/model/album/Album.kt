package com.myapps.mymusic.data.remote.model.album

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    val id:Long,
    @SerializedName("title")
    val title:String,
    @SerializedName("cover")
    val cover:String,
    @SerializedName("cover_xl")
    val coverXL:String,
)


