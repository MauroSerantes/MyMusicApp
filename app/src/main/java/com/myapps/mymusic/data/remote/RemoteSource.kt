package com.myapps.mymusic.data.remote

import com.myapps.mymusic.core.mappers.toDto
import com.myapps.mymusic.core.mappers.toDtoNullAlbum
import com.myapps.mymusic.data.model.AlbumDto
import com.myapps.mymusic.data.model.ArtistDto
import com.myapps.mymusic.data.model.GenreDto
import com.myapps.mymusic.data.model.PlaylistDto
import com.myapps.mymusic.data.model.TrackDto
import com.myapps.mymusic.data.remote.service.DeezerApiService
import javax.inject.Inject

class RemoteSource @Inject constructor(private val deezerApiService: DeezerApiService) {

    suspend fun getGenres(): DataStatus<List<GenreDto>>{
        val result = deezerApiService.getGenres()
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getTopPlaylists(): DataStatus<List<PlaylistDto>>{
        val result = deezerApiService.getTopPlaylists()
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getTopTracks(): DataStatus<List<TrackDto>>{
        val result = deezerApiService.getTopTracks()
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getTopAlbums(): DataStatus<List<AlbumDto>>{
        val result = deezerApiService.getTopAlbums()
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getTopArtists(): DataStatus<List<ArtistDto>>{
        val result = deezerApiService.getTopArtists()
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getTracksFromAlbum(id:Long): DataStatus<List<TrackDto>>{
        val result = deezerApiService.getTracksFromAlbumId(id)
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDtoNullAlbum()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getTracksOfArtist(id:Long): DataStatus<List<TrackDto>>{
        val result = deezerApiService.getTracksFromArtistId(id)
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getTracksFromPlaylist(id:Long): DataStatus<List<TrackDto>>{
        val result = deezerApiService.getTracksFromPlaylist(id)
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getArtistsByGenre(id:Long): DataStatus<List<ArtistDto>>{
        val result = deezerApiService.getArtistsByGenreId(id)
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getArtistsBySearch(artistName:String): DataStatus<List<ArtistDto>>{
        val result = deezerApiService.getArtistsBySearch(artistName)
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }
    suspend fun getAlbumsBySearch(albumName:String):DataStatus<List<AlbumDto>>{
        val result = deezerApiService.getAlbumsBySearch(albumName)
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getTracksBySearch(trackName:String): DataStatus<List<TrackDto>>{
        val result = deezerApiService.getTracksBySearch(trackName)
        if(result.isSuccessful){
            return  DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }?: emptyList()
            )
        }
        return DataStatus.error(result.message())
    }
}