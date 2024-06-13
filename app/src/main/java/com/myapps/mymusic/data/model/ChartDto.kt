package com.myapps.mymusic.data.model

data class ChartDto(
    val topTracks:List<TrackDto>,
    val topArtists:List<ArtistDto>,
    val topAlbums:List<AlbumDto>,
    val topPlaylists:List<PlaylistDto>
)
