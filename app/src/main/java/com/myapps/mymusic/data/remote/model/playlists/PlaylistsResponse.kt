package com.myapps.mymusic.data.remote.model.playlists

import com.google.gson.annotations.SerializedName

data class PlaylistsResponse(
    @SerializedName("data")
    val data:List<Playlist>,
)



