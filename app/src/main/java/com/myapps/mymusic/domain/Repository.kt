package com.myapps.mymusic.domain

import com.myapps.mymusic.data.remote.DataStatus
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getGenres():Flow<DataStatus<List<GenreModel>>>
    fun getTopPlaylists():Flow<DataStatus<List<PlaylistModel>>>
    fun getTopTracks():Flow<DataStatus<List<TrackModel>>>
    fun getTopAlbums():Flow<DataStatus<List<AlbumModel>>>
    fun getTopArtists():Flow<DataStatus<List<ArtistModel>>>
    fun getTracksFromAlbum(id:Long):Flow<DataStatus<List<TrackModel>>>
    fun getTracksOfArtist(id:Long):Flow<DataStatus<List<TrackModel>>>
    fun getTracksFromPlaylist(id:Long):Flow<DataStatus<List<TrackModel>>>
    fun getArtistsByGenre(id:Long):Flow<DataStatus<List<ArtistModel>>>
    fun getArtistsBySearch(artistName:String):Flow<DataStatus<List<ArtistModel>>>
    fun getAlbumsBySearch(albumName:String):Flow<DataStatus<List<AlbumModel>>>
    fun getTracksBySearch(trackName:String):Flow<DataStatus<List<TrackModel>>>
    suspend fun addTrackToFavourites(track:TrackModel)
    suspend fun deleteTrackFromFavourites(track:TrackModel)
    fun getFavouritesTracks():Flow<DataStatus<List<TrackModel>>>
    suspend fun trackIsAlreadyAFavourite(track:TrackModel):Boolean
}



