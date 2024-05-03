package com.myapps.mymusic.domain


import com.myapps.mymusic.data.remote.DataStatus
import kotlinx.coroutines.flow.Flow


interface Repository {

    fun getTracksFromRadioId(id:Long):Flow<DataStatus<List<TrackModel>>>
    fun getRadiosByGenreId(id:Long):Flow<DataStatus<List<RadioModel>>>
    fun getTopRadios():Flow<DataStatus<List<RadioModel>>>
    fun getGenres():Flow<DataStatus<List<GenreModel>>>
    fun getChart():Flow<DataStatus<ChartModel>>
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
    fun getSearchTracksCache():Flow<List<TrackModel>>
    fun getSearchAlbumCache():Flow<List<AlbumModel>>
    fun getSearchArtistsCache():Flow<List<ArtistModel>>
    suspend fun upsertTrackSearchCache(track:TrackModel)
    suspend fun upsertAlbumSearchCache(album:AlbumModel)
    suspend fun upsertArtistSearchCache(artist:ArtistModel)
    suspend fun deleteTrackSearchCache(track:TrackModel)
    suspend fun deleteAlbumSearchCache(album:AlbumModel)
    suspend fun deleteArtistSearchCache(artist:ArtistModel)
    suspend fun deleteAllTrackSearchCache(typeOfCache:Int)
    suspend fun deleteAllAlbumSearchCache(typeOfCache:Int)
    suspend fun deleteAllArtistSearchCache(typeOfCache:Int)
}



