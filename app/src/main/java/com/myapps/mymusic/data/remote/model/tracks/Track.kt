package com.myapps.mymusic.data.remote.model.tracks

import com.google.gson.annotations.SerializedName
import com.myapps.mymusic.data.remote.model.album.Album
import com.myapps.mymusic.data.remote.model.artists.Artist

data class Track(
    @SerializedName("title")
    val title:String,
    @SerializedName("preview")
    val preview:String,
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("album")
    var album: Album
)
