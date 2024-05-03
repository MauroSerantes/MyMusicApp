package com.myapps.mymusic.data.remote

import androidx.compose.runtime.rememberUpdatedState
import com.myapps.mymusic.core.mappers.toDto
import com.myapps.mymusic.core.mappers.toDtoNullAlbum
import com.myapps.mymusic.data.model.AlbumDto
import com.myapps.mymusic.data.model.ArtistDto
import com.myapps.mymusic.data.model.ChartDto
import com.myapps.mymusic.data.model.GenreDto
import com.myapps.mymusic.data.model.RadioDto
import com.myapps.mymusic.data.model.TrackDto
import com.myapps.mymusic.data.remote.model.radio.RadioResponse
import com.myapps.mymusic.data.remote.service.DeezerApiService
import com.myapps.mymusic.domain.RadioModel
import retrofit2.Response
import retrofit2.http.Path
import javax.inject.Inject

class RemoteSource @Inject constructor(private val deezerApiService: DeezerApiService) {


    suspend fun getTracksFromRadiosId(id:Long): DataStatus<List<TrackDto>>{
        val result = deezerApiService.getTracksFromRadiosId(id)
        if(result.isSuccessful){
            return DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }
            )
        }
        return DataStatus.error(result.message())
    }

    suspend fun getRadiosByGenreId(id:Long):DataStatus<List<RadioDto>>{
        val result = deezerApiService.getRadiosByGenreId(id)
        if(result.isSuccessful){
            return DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }
            )
        }
        return DataStatus.error(result.message())
    }
    suspend fun getTopRadios():DataStatus<List<RadioDto>>{
        val result = deezerApiService.getTopRadios()
        if(result.isSuccessful){
            return DataStatus.success(
                result.body()?.data?.map {
                    it.toDto()
                }
            )
        }
        return DataStatus.error(result.message())
    }
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

    suspend fun getChart():DataStatus<ChartDto>{
        val result = deezerApiService.getChart()
        if(result.isSuccessful){
            return DataStatus.success(
                result.body()?.toDto()
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