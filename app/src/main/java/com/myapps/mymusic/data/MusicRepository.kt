package com.myapps.mymusic.data

import com.myapps.mymusic.data.local.LocalSource
import com.myapps.mymusic.data.remote.DataStatus
import com.myapps.mymusic.data.remote.RemoteSource
import com.myapps.mymusic.data.remote.Status
import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.GenreModel
import com.myapps.mymusic.domain.PlaylistModel
import com.myapps.mymusic.domain.Repository
import com.myapps.mymusic.domain.TrackModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.myapps.mymusic.core.mappers.toDomain
import com.myapps.mymusic.core.mappers.toDomainNullAlbum
import com.myapps.mymusic.core.mappers.toDto
import com.myapps.mymusic.data.model.TrackDto
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class MusicRepository @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
    ):Repository{

    override fun getGenres(): Flow<DataStatus<List<GenreModel>>> = flow {
        val result = remoteSource.getGenres()
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getTopPlaylists(): Flow<DataStatus<List<PlaylistModel>>> = flow{
        val result = remoteSource.getTopPlaylists()
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getTopTracks(): Flow<DataStatus<List<TrackModel>>> = flow{
        val result = remoteSource.getTopTracks()
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getTopAlbums(): Flow<DataStatus<List<AlbumModel>>> = flow{
        val result = remoteSource.getTopAlbums()
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getTopArtists(): Flow<DataStatus<List<ArtistModel>>> = flow{
        val result = remoteSource.getTopArtists()
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getTracksFromAlbum(id: Long): Flow<DataStatus<List<TrackModel>>> = flow{
        val result = remoteSource.getTracksFromAlbum(id)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomainNullAlbum() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getTracksOfArtist(id: Long): Flow<DataStatus<List<TrackModel>>> = flow{
        val result = remoteSource.getTracksOfArtist(id)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getTracksFromPlaylist(id: Long): Flow<DataStatus<List<TrackModel>>> = flow{
        val result = remoteSource.getTracksFromPlaylist(id)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getArtistsByGenre(id: Long): Flow<DataStatus<List<ArtistModel>>> = flow{
        val result = remoteSource.getArtistsByGenre(id)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getArtistsBySearch(artistName: String): Flow<DataStatus<List<ArtistModel>>> = flow{
        val result = remoteSource.getArtistsBySearch(artistName)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getAlbumsBySearch(albumName: String): Flow<DataStatus<List<AlbumModel>>> = flow{
        val result = remoteSource.getAlbumsBySearch(albumName)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getTracksBySearch(trackName: String): Flow<DataStatus<List<TrackModel>>> = flow{
        val result = remoteSource.getTracksBySearch(trackName)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override suspend fun addTrackToFavourites(track: TrackModel){
        localSource.addTrackToFavourites(track.toDto())
    }

    override suspend fun deleteTrackFromFavourites(track: TrackModel) {
        localSource.deleteTrackFromFavourites(track.toDto())
    }

    override fun getFavouritesTracks(): Flow<DataStatus<List<TrackModel>>> = flow {
        var result:List<TrackDto>

        localSource.getFavouritesTracks().collect{
            result = it
            emit(DataStatus.success(result.map { it.toDomain() }))
        }

    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }


    override suspend fun trackIsAlreadyAFavourite(track: TrackModel): Boolean {
        return localSource.trackIsAlreadyAFavourite(track.toDto())
    }
}
