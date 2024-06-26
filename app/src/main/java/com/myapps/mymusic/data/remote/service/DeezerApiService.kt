package com.myapps.mymusic.data.remote.service


import com.myapps.mymusic.data.remote.model.album.AlbumsResponse
import com.myapps.mymusic.data.remote.model.artists.ArtistsResponse
import com.myapps.mymusic.data.remote.model.chart.ChartResponse
import com.myapps.mymusic.data.remote.model.genre.GenresResponse
import com.myapps.mymusic.data.remote.model.playlists.PlaylistsResponse
import com.myapps.mymusic.data.remote.model.radio.RadioResponse
import com.myapps.mymusic.data.remote.model.tracks.TracksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {
    @GET("genre")
    suspend fun getGenres():Response<GenresResponse>
    @GET("chart")
    suspend fun getChart():Response<ChartResponse>

    @GET("radio/top")
    suspend fun getTopRadios():Response<RadioResponse>

    @GET("album/{id}/tracks")
    suspend fun getTracksFromAlbumId(@Path("id") id:Long):Response<TracksResponse>

    @GET("artist/{id}/top?limit=${Int.MAX_VALUE})")
    suspend fun getTracksFromArtistId(@Path("id") id:Long):Response<TracksResponse>

    @GET("playlist/{id}/tracks?limit=${Int.MAX_VALUE}")
    suspend fun getTracksFromPlaylist(@Path("id")id:Long):Response<TracksResponse>

    @GET("genre/{id}/artists")
    suspend fun getArtistsByGenreId(@Path("id") id:Long):Response<ArtistsResponse>

    @GET("genre/{id}/radios")
    suspend fun getRadiosByGenreId(@Path("id")id:Long):Response<RadioResponse>

    @GET("radio/{id}/tracks?limit=${Int.MAX_VALUE}")
    suspend fun getTracksFromRadiosId(@Path("id")id:Long):Response<TracksResponse>

    @GET("search/artist")
    suspend fun getArtistsBySearch(@Query("q") artist:String):Response<ArtistsResponse>

    @GET("search/album")
    suspend fun getAlbumsBySearch(@Query("q") album:String):Response<AlbumsResponse>

    @GET("search/track")
    suspend fun getTracksBySearch(@Query("q") track:String):Response<TracksResponse>
}


