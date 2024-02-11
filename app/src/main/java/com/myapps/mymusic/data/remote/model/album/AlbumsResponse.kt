package com.myapps.mymusic.data.remote.model.album

import com.google.gson.annotations.SerializedName
import com.myapps.mymusic.data.remote.model.album.Album

data class AlbumsResponse(
    @SerializedName("data")
    val data:List<Album>
)
