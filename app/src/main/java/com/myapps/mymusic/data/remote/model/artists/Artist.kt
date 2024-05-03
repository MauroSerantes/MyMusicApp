package com.myapps.mymusic.data.remote.model.artists

import com.google.gson.annotations.SerializedName
import com.myapps.mymusic.data.model.ArtistDto

data class Artist(
    @SerializedName("id")
    val id:Long,
    @SerializedName("name")
    val name:String,
    @SerializedName("picture")
    val picture:String?,
    @SerializedName("picture_xl")
    val pictureXL:String?,
)

