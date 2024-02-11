package com.myapps.mymusic.core.mappers

import com.myapps.mymusic.data.remote.model.album.Album
import com.myapps.mymusic.data.remote.model.artists.Artist
import com.myapps.mymusic.data.remote.model.genre.Genre
import com.myapps.mymusic.data.local.model.FavouriteTrack
import com.myapps.mymusic.data.model.AlbumDto
import com.myapps.mymusic.data.model.ArtistDto
import com.myapps.mymusic.data.model.GenreDto
import com.myapps.mymusic.data.model.PlaylistDto
import com.myapps.mymusic.data.model.TrackDto
import com.myapps.mymusic.data.remote.model.playlists.Playlist
import com.myapps.mymusic.data.remote.model.tracks.Track
import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.GenreModel
import com.myapps.mymusic.domain.PlaylistModel
import com.myapps.mymusic.domain.TrackModel

// to Dto
fun Track.toDto():TrackDto = TrackDto(
        title = this.title,
        preview = this.preview,
        artistName = this.artist.name,
        albumName = this.album.title,
        albumCover = this.album.coverXL
)

fun Track.toDtoNullAlbum():TrackDto = TrackDto(
        title = this.title,
        preview = this.preview,
        artistName = this.artist.name,
        albumName = "",
        albumCover = ""
)

fun Genre.toDto():GenreDto = GenreDto(
        id = this.id,
        name = this.name,
        picture = this.picture,
        pictureBig = this.pictureBig
)

fun Artist.toDto(): ArtistDto = ArtistDto(
        id = this.id,
        name = this.name,
        picture = this.picture,
        pictureXL = this.pictureXL
)

fun Album.toDto(): AlbumDto = AlbumDto(
        id = this.id,
        title = this.title,
        cover = this.cover,
        coverXL = this.coverXL,
)

fun Playlist.toDto(): PlaylistDto = PlaylistDto(
        id = this.id,
        title = this.title,
        picture = this.picture,
        pictureXL = this.pictureXL
)

fun FavouriteTrack.toDto():TrackDto = TrackDto(
        title = this.title,
        preview = this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        albumCover = this.albumCover
)

//toDomain
fun TrackDto.toDomain():TrackModel = TrackModel(
        title = this.title,
        preview = this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        albumCover = this.albumCover
)

fun TrackDto.toDomainNullAlbum():TrackModel = TrackModel(
        title = this.title,
        preview = this.preview,
        artistName = this.artistName,
        albumName = "",
        albumCover = ""
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

fun TrackDto.toFavouriteTrack():FavouriteTrack = FavouriteTrack(
        title = this.title,
        preview = this.preview,
        artistName = this.artistName,
        albumName = this.albumName,
        albumCover = this.albumCover
)

//to Dto From Domain

fun TrackModel.toDto():TrackDto = TrackDto(
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
