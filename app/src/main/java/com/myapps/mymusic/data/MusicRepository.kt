package com.myapps.mymusic.data



import com.myapps.mymusic.data.local.LocalSource
import com.myapps.mymusic.data.remote.DataStatus
import com.myapps.mymusic.data.remote.RemoteSource
import com.myapps.mymusic.data.remote.Status
import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.GenreModel
import com.myapps.mymusic.domain.Repository
import com.myapps.mymusic.domain.TrackModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.myapps.mymusic.core.mappers.toDomain
import com.myapps.mymusic.core.mappers.toDto
import com.myapps.mymusic.data.local.model.TypeOfAlbumCache
import com.myapps.mymusic.data.local.model.TypeOfArtistCache
import com.myapps.mymusic.data.local.model.TypeOfTrackCache
import com.myapps.mymusic.data.model.ChartDto
import com.myapps.mymusic.data.model.TrackDto
import com.myapps.mymusic.data.utils.NetworkObserver
import com.myapps.mymusic.domain.ChartModel
import com.myapps.mymusic.domain.RadioModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map


class MusicRepository @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val networkObserver: NetworkObserver
):Repository{
    override fun getTracksFromRadioId(id: Long): Flow<DataStatus<List<TrackModel>>> = flow {
        val result = remoteSource.getTracksFromRadiosId(id)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }


    override fun getRadiosByGenreId(id: Long): Flow<DataStatus<List<RadioModel>>> = flow{
        val result = remoteSource.getRadiosByGenreId(id)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        else{
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getTopRadios(): Flow<DataStatus<List<RadioModel>>> = flow<DataStatus<List<RadioModel>>>{
        networkObserver.observeStatus().collect{ status->
            when(status){
                NetworkObserver.Status.Connected->{
                    val result = remoteSource.getTopRadios()
                    if(result.status == Status.SUCCESS){
                        localSource.deleteRadioCache()
                        localSource.upsertRadioCache(result.data!!)
                        localSource.getRadioCache().collect{ list->
                            emit(DataStatus.success(list.map { it.toDomain() }))
                        }
                    }
                    else{
                        emit(DataStatus.error(result.messageError))
                    }
                }
                else ->{
                    if(localSource.thereIsRadioCacheInfo()){
                        localSource.getRadioCache().collect{ list->
                            emit(DataStatus.success(list.map { it.toDomain() }))
                        }
                    }
                    else{
                        emit(DataStatus.error("No Internet Connection"))
                    }
                }
            }
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getGenres(): Flow<DataStatus<List<GenreModel>>> = flow<DataStatus<List<GenreModel>>>{
        networkObserver.observeStatus().collect{ status->
            when(status){
               NetworkObserver.Status.Connected->{
                    val result = remoteSource.getGenres()
                    if(result.status == Status.SUCCESS){
                        localSource.deleteGenresCache()
                        localSource.upsertGenresCache(result.data!!)
                        localSource.getGenresCache().collect{ list->
                            emit(DataStatus.success(list.map { it.toDomain() }))
                        }
                    }
                    else{
                        emit(DataStatus.error(result.messageError))
                    }
                }
                else ->{
                    if(localSource.thereIsGenreCacheInfo()){
                        localSource.getGenresCache().collect{ list->
                            emit(DataStatus.success(list.map { it.toDomain() }))
                        }
                    }
                    else{
                        emit(DataStatus.error("No Internet Connection"))
                    }
                }
            }
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getChart(): Flow<DataStatus<ChartModel>> = flow <DataStatus<ChartModel>>{
        networkObserver.observeStatus().collect{status->
            when(status){
                NetworkObserver.Status.Connected->{
                    val result = remoteSource.getChart()
                    if(result.status == Status.SUCCESS){
                        updateCacheData(result.data!!)
                        getChartLocalCacheData().collect{
                            emit(DataStatus.success(it.toDomain()))
                        }
                    }
                    else{
                        emit(DataStatus.error(result.messageError))
                    }

                }
                else->{
                    if(thereIsChartCacheData()){
                        getChartLocalCacheData().collect{
                            emit(DataStatus.success(it.toDomain()))
                        }
                    }
                    else{
                       emit(DataStatus.error("No Internet Connection"))
                    }

                }
            }
        }
    }

    private suspend fun updateCacheData(chartData:ChartDto){
        localSource.deleteAlbumsCache(TypeOfAlbumCache.TOP_ALBUM)
        localSource.deleteTracksCache(TypeOfTrackCache.TOP_TRACK)
        localSource.deletePlaylistsCache()
        localSource.deleteArtistsCache(TypeOfArtistCache.TOP_ARTIST)

        localSource.upsertAlbumsCache(chartData.topAlbums,TypeOfAlbumCache.TOP_ALBUM)
        localSource.upsertTracksCache(chartData.topTracks,TypeOfAlbumCache.TOP_ALBUM)
        localSource.upsertPlaylistsCache(chartData.topPlaylists)
        localSource.upsertArtistsCache(chartData.topArtists,TypeOfArtistCache.TOP_ARTIST)

    }
    private fun getChartLocalCacheData():Flow<ChartDto>{
        val tracks = localSource.getTracksCache(TypeOfTrackCache.TOP_TRACK)
        val albums = localSource.getAlbumsCache(TypeOfAlbumCache.TOP_ALBUM)
        val artists = localSource.getArtistsCache(TypeOfArtistCache.TOP_ARTIST)
        val playlists = localSource.getPlaylistsCache()


        return combine(tracks,albums,artists,playlists){
            track,album,artist,playlist->
            ChartDto(track,artist,album,playlist)
        }
    }

    private suspend fun thereIsChartCacheData():Boolean = localSource.thereIsAlbumCacheInfo(TypeOfAlbumCache.TOP_ALBUM) &&
                localSource.thereIsArtistCacheInfo(TypeOfArtistCache.TOP_ARTIST) &&
                localSource.thereIsTracksCacheInfo(TypeOfTrackCache.TOP_TRACK) &&
                localSource.thereIsPlaylistCacheInfo()

    override fun getTracksFromAlbum(id: Long): Flow<DataStatus<List<TrackModel>>> = flow<DataStatus<List<TrackModel>>>{
        val result = remoteSource.getTracksFromAlbum(id)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        if(result.status == Status.ERROR){
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }



    override fun getTracksOfArtist(id: Long): Flow<DataStatus<List<TrackModel>>> = flow<DataStatus<List<TrackModel>>>{
        val result = remoteSource.getTracksOfArtist(id)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain() }))
        }
        if(result.status == Status.ERROR){
            emit(DataStatus.error(result.messageError))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DataStatus.error(it.message.toString()))
    }

    override fun getTracksFromPlaylist(id: Long): Flow<DataStatus<List<TrackModel>>> = flow<DataStatus<List<TrackModel>>>{
        val result = remoteSource.getTracksFromPlaylist(id)
        if(result.status == Status.SUCCESS){
            emit(DataStatus.success(result.data?.map { it.toDomain()}))
        }
        if(result.status == Status.ERROR){
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

    override fun getSearchTracksCache(): Flow<List<TrackModel>> = localSource.getTracksCache(TypeOfTrackCache.SEARCH_TRACK).map { list->
        list.map { it.toDomain() }
    }

    override fun getSearchAlbumCache(): Flow<List<AlbumModel>> = localSource.getAlbumsCache(TypeOfAlbumCache.SEARCH_ALBUM).map { list->
        list.map { it.toDomain() }
    }

    override fun getSearchArtistsCache(): Flow<List<ArtistModel>> = localSource.getArtistsCache(TypeOfArtistCache.SEARCH_ARTIST).map { list->
        list.map { it.toDomain() }
    }

    override suspend fun upsertTrackSearchCache(track: TrackModel) {
        localSource.upsertTracksCache(listOf(track.toDto()),TypeOfTrackCache.SEARCH_TRACK)
    }

    override suspend fun upsertAlbumSearchCache(album: AlbumModel) {
        localSource.upsertAlbumsCache(listOf(album.toDto()),TypeOfAlbumCache.SEARCH_ALBUM)
    }

    override suspend fun upsertArtistSearchCache(artist: ArtistModel) {
        localSource.upsertArtistsCache(listOf(artist.toDto()),TypeOfArtistCache.SEARCH_ARTIST)
    }

    override suspend fun deleteTrackSearchCache(track: TrackModel){
        localSource.deleteTrackCache(track.id, TypeOfTrackCache.SEARCH_TRACK)
    }

    override suspend fun deleteAlbumSearchCache(album: AlbumModel){
        localSource.deleteAlbumCache(album.id,TypeOfAlbumCache.SEARCH_ALBUM)
    }

    override suspend fun deleteArtistSearchCache(artist: ArtistModel){
        localSource.deleteArtistCache(artist.id,TypeOfArtistCache.SEARCH_ARTIST)
    }

    override suspend fun deleteAllTrackSearchCache(typeOfCache: Int) {
        localSource.deleteTracksCache(typeOfCache)
    }

    override suspend fun deleteAllAlbumSearchCache(typeOfCache: Int) {
        localSource.deleteAlbumsCache(typeOfCache)
    }

    override suspend fun deleteAllArtistSearchCache(typeOfCache: Int) {
        localSource.deleteArtistsCache(typeOfCache)
    }
}
