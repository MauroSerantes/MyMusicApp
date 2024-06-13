package com.myapps.mymusic.data.remote.model.chart

import com.google.gson.annotations.SerializedName
import com.myapps.mymusic.data.remote.model.album.AlbumsResponse
import com.myapps.mymusic.data.remote.model.artists.ArtistsResponse
import com.myapps.mymusic.data.remote.model.playlists.PlaylistsResponse
import com.myapps.mymusic.data.remote.model.tracks.TracksResponse

data class ChartResponse(
    @SerializedName("tracks")
    val tracks:TracksResponse,
    @SerializedName("albums")
    val albums: AlbumsResponse,
    @SerializedName("artists")
    val artists: ArtistsResponse,
    @SerializedName("playlists")
    val playlists: PlaylistsResponse
)
