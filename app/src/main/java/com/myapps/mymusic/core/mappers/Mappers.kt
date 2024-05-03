package com.myapps.mymusic.core.mappers

import com.myapps.mymusic.data.local.model.AlbumCache
import com.myapps.mymusic.data.local.model.ArtistCache
import com.myapps.mymusic.data.remote.model.album.Album
import com.myapps.mymusic.data.remote.model.artists.Artist
import com.myapps.mymusic.data.remote.model.genre.Genre
import com.myapps.mymusic.data.local.model.FavouriteTrack
import com.myapps.mymusic.data.local.model.GenreCache
import com.myapps.mymusic.data.local.model.PlaylistCache
import com.myapps.mymusic.data.local.model.RadioCache
import com.myapps.mymusic.data.local.model.TrackCache
import com.myapps.mymusic.data.model.AlbumDto
import com.myapps.mymusic.data.model.ArtistDto
import com.myapps.mymusic.data.model.ChartDto
import com.myapps.mymusic.data.model.GenreDto
import com.myapps.mymusic.data.model.PlaylistDto
import com.myapps.mymusic.data.model.RadioDto
import com.myapps.mymusic.data.model.TrackDto
import com.myapps.mymusic.data.remote.model.chart.ChartResponse
import com.myapps.mymusic.data.remote.model.playlists.Playlist
import com.myapps.mymusic.data.remote.model.radio.Radio
import com.myapps.mymusic.data.remote.model.tracks.Track
import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.ChartModel
import com.myapps.mymusic.domain.GenreModel
import com.myapps.mymusic.domain.PlaylistModel
import com.myapps.mymusic.domain.RadioModel
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.search.model.ResultModel

//from Dto to Data

fun RadioDto.toRadioCache():RadioCache = RadioCache(
        id = this.id,
        title = this.title,
        picture = this.picture,
        pictureXL = this.pictureXL
)
fun TrackDto.toFavouriteTrack():FavouriteTrack = FavouriteTrack(
        trackId = this.id,
        title = this.title,
        preview = this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        albumCover = this.albumCover
)

fun AlbumDto.toAlbumCache(typeOfCache:Int): AlbumCache = AlbumCache(
        id = this.id,
        title = this.title,
        cover = this.cover,
        coverXl = this.coverXL,
        typeOfCache = typeOfCache
)

fun ArtistDto.toArtistCache(typeOfCache: Int):ArtistCache = ArtistCache(
        id = this.id,
        name = this.name,
        picture = this.picture,
        pictureXl = this.pictureXL,
        typeOfCache = typeOfCache
)
fun GenreDto.toGenreCache():GenreCache = GenreCache(
        id = this.id,
        name = this.name,
        picture = this.picture,
        pictureBig = this.pictureBig
)

fun PlaylistDto.toPlaylistCache():PlaylistCache = PlaylistCache(
        id = this.id,
        title = this.title,
        picture = this.picture,
        pictureXl = this.pictureXL
)

fun TrackDto.toTrackCache(typeOfCache: Int):TrackCache = TrackCache(
        id = this.id,
        title = this.title,
        preview =  this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        albumCover = this.albumCover,
        typeOfCache = typeOfCache
)


// to Dto

fun Radio.toDto():RadioDto = RadioDto(
        id = this.id,
        title = this.title,
        picture = this.picture?:"",
        pictureXL = this.pictureXL?:""
)
fun ChartResponse.toDto():ChartDto = ChartDto(
        topAlbums = this.albums.data.map { it.toDto() },
        topArtists = this.artists.data.map { it.toDto() },
        topPlaylists = this.playlists.data.map { it.toDto() },
        topTracks = this.tracks.data.map { it.toDto() }
)
fun Track.toDto():TrackDto = TrackDto(
        id = this.id,
        title = this.title,
        preview = this.preview,
        artistName = this.artist.name,
        albumName = this.album.title,
        albumCover = this.album.coverXL?:""
)

fun Track.toDtoNullAlbum():TrackDto = TrackDto(
        id = this.id,
        title = this.title,
        preview = this.preview,
        artistName = this.artist.name,
        albumName = "",
        albumCover = ""
)

fun Genre.toDto():GenreDto = GenreDto(
        id = this.id,
        name = this.name,
        picture = this.picture?:"",
        pictureBig = this.pictureBig?:""
)

fun Album.toDto(): AlbumDto = AlbumDto(
        id = this.id,
        title = this.title,
        cover = this.cover?:"",
        coverXL = this.coverXL?:"",
)
fun Artist.toDto(): ArtistDto = ArtistDto(
        id = this.id,
        name = this.name,
        picture = this.picture?:"",
        pictureXL = this.pictureXL?:""
)


fun Playlist.toDto(): PlaylistDto = PlaylistDto(
        id = this.id,
        title = this.title,
        picture = this.picture?:"",
        pictureXL = this.pictureXL?:""
)

fun FavouriteTrack.toDto():TrackDto = TrackDto(
        id = this.trackId,
        title = this.title,
        preview = this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        albumCover = this.albumCover
)

fun AlbumCache.toDto():AlbumDto = AlbumDto(
        id = this.id,
        title = this.title,
        cover = this.cover,
        coverXL = this.coverXl
)

fun ArtistCache.toDto():ArtistDto = ArtistDto(
        id = this.id,
        name =  this.name,
        picture = this.picture,
        pictureXL = this.pictureXl
)

fun GenreCache.toDto():GenreDto = GenreDto(
        id = this.id,
        name = this.name,
        picture = this.picture,
        pictureBig = this.pictureBig
)

fun PlaylistCache.toDto():PlaylistDto = PlaylistDto(
        id = this.id,
        title = this.title,
        picture = this.picture,
        pictureXL = this.pictureXl
)

fun TrackCache.toDto():TrackDto = TrackDto(
        id = this.id,
        title = this.title,
        preview = this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        albumCover = this.albumCover
)

fun RadioCache.toDto():RadioDto = RadioDto(
        id  = this.id,
        title = this.title,
        picture = this.picture,
        pictureXL = this.pictureXL
)


//toDomain
fun RadioDto.toDomain():RadioModel = RadioModel(
        id = this.id,
        title = this.title,
        picture = this.picture,
        pictureXL = this.pictureXL
)

fun TrackDto.toDomain():TrackModel = TrackModel(
        id = this.id,
        title = this.title,
        preview = this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        albumCover = this.albumCover
)
fun GenreDto.toDomain():GenreModel = GenreModel(
        id = this.id,
        name = this.name,
        picture = this.picture,
        pictureBig = this.pictureBig
)

fun ArtistDto.toDomain():ArtistModel = ArtistModel(
        id = this.id,
        name = this.name,
        picture = this.picture,
        pictureXL = this.pictureXL
)

fun AlbumDto.toDomain():AlbumModel = AlbumModel(
        id = this.id,
        title = this.title,
        cover = this.cover,
        coverXL = this.coverXL,
)

fun PlaylistDto.toDomain():PlaylistModel = PlaylistModel(
        id = this.id,
        title = this.title,
        picture = this.picture,
        pictureXL = this.pictureXL
)

//to Dto From Domain

fun ChartDto.toDomain():ChartModel = ChartModel(
        topTracks = this.topTracks.map { it.toDomain() },
        topPlaylists = this.topPlaylists.map { it.toDomain() },
        topArtists = this.topArtists.map { it.toDomain() },
        topAlbums = this.topAlbums.map { it.toDomain() }
)
fun TrackModel.toDto():TrackDto = TrackDto(
        id = this.id,
        title = this.title,
        preview = this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        albumCover = this.albumCover
)


fun GenreModel.toDto():GenreDto = GenreDto(
        id = this.id,
        name = this.name,
        picture = this.picture,
        pictureBig = this.pictureBig
)

fun ArtistModel.toDto():ArtistDto = ArtistDto(
        id = this.id,
        name = this.name,
        picture = this.picture,
        pictureXL = this.pictureXL
)

fun AlbumModel.toDto():AlbumDto = AlbumDto(
        id = this.id,
        title = this.title,
        cover = this.cover,
        coverXL = this.coverXL,
)

fun PlaylistModel.toDto():PlaylistDto = PlaylistDto(
        id = this.id,
        title = this.title,
        picture = this.picture,
        pictureXL = this.pictureXL
)


fun TrackModel.toResult(): ResultModel = ResultModel(
        id = this.id,
        title =  this.title,
        pictureXL =  this.albumCover,
        preview = this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        type = "track"
)

fun ArtistModel.toResult(): ResultModel = ResultModel(
        id = this.id,
        title = this.name,
        picture = this.picture,
        pictureXL = this.pictureXL,
        type = "artist"
)

fun AlbumModel.toResult(): ResultModel = ResultModel(
        id = this.id,
        title = this.title,
        picture = this.cover,
        pictureXL = this.coverXL,
        type = "album"
)

fun ResultModel.toArtistModel():ArtistModel = ArtistModel(
        id = this.id,
        name = this.title,
        picture = this.picture,
        pictureXL = this.pictureXL
)

fun ResultModel.toAlbumModel():AlbumModel = AlbumModel(
        id = this.id,
        title = this.title,
        cover = this.picture,
        coverXL = this.pictureXL
)

fun ResultModel.toTrackModel():TrackModel = TrackModel(
        id = this.id,
        title = this.title,
        preview = this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        albumCover = this.pictureXL
)