package com.myapps.mymusic.domain


data class ChartModel(
    val topTracks:List<TrackModel>,
    val topArtists:List<ArtistModel>,
    val topAlbums:List<AlbumModel>,
    val topPlaylists:List<PlaylistModel>
)

val emptyChart = ChartModel(
    emptyList(),
    emptyList(),
    emptyList(),
    emptyList()
)
