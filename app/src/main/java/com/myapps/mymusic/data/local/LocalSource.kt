package com.myapps.mymusic.data.local


import com.myapps.mymusic.core.mappers.toAlbumCache
import com.myapps.mymusic.core.mappers.toArtistCache
import com.myapps.mymusic.core.mappers.toDto
import com.myapps.mymusic.core.mappers.toFavouriteTrack
import com.myapps.mymusic.core.mappers.toGenreCache
import com.myapps.mymusic.core.mappers.toPlaylistCache
import com.myapps.mymusic.core.mappers.toRadioCache
import com.myapps.mymusic.core.mappers.toTrackCache
import com.myapps.mymusic.data.local.service.AlbumCacheDao
import com.myapps.mymusic.data.local.service.ArtistCacheDao
import com.myapps.mymusic.data.local.service.GenreCacheDao
import com.myapps.mymusic.data.local.service.PlaylistCacheDao
import com.myapps.mymusic.data.local.service.RadioCacheDao
import com.myapps.mymusic.data.local.service.TracksCacheDao
import com.myapps.mymusic.data.local.service.TracksDao
import com.myapps.mymusic.data.model.AlbumDto
import com.myapps.mymusic.data.model.ArtistDto
import com.myapps.mymusic.data.model.GenreDto
import com.myapps.mymusic.data.model.PlaylistDto
import com.myapps.mymusic.data.model.RadioDto
import com.myapps.mymusic.data.model.TrackDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalSource @Inject constructor(
    private val trackDao: TracksDao,
    private val trackCacheDao:TracksCacheDao,
    private val albumCacheDao: AlbumCacheDao,
    private val artistCacheDao: ArtistCacheDao,
    private val playlistCacheDao: PlaylistCacheDao,
    private val genreCacheDao: GenreCacheDao,
    private val radioCacheDao: RadioCacheDao
) {

    suspend fun upsertRadioCache(radioListCache: List<RadioDto>) {
        radioCacheDao.upsertRadioCache(radioListCache.map {
            it.toRadioCache()
        })
    }

    suspend fun deleteRadioCache() {
        radioCacheDao.deleteRadioCache()
    }

     fun getRadioCache(): Flow<List<RadioDto>> {
        return radioCacheDao.getRadioCache().map { list->
            list.map { it.toDto() }
        }
    }

    suspend fun thereIsRadioCacheInfo(): Boolean {
        return radioCacheDao.radioCacheCount() != 0
    }

    suspend fun upsertTracksCache(tracksCache: List<TrackDto>, typeOfCache: Int) {
        trackCacheDao.upsertTracksCache(tracksCache.map {
            it.toTrackCache(typeOfCache)
        })
    }

    suspend fun deleteTracksCache(typeOfCache: Int) {
        trackCacheDao.deleteTracksCache(typeOfCache)
    }

    fun getTracksCache(typeOfCache: Int): Flow<List<TrackDto>> =
        trackCacheDao.getTrackCache(typeOfCache).map { list ->
            list.map { it.toDto() }
        }

    suspend fun upsertPlaylistsCache(playlistListCache: List<PlaylistDto>) {
        playlistCacheDao.upsertPlaylistsCache(playlistListCache.map {
            it.toPlaylistCache()
        })
    }

    suspend fun deletePlaylistsCache() {
        playlistCacheDao.deletePlaylistsCache()
    }

   fun getPlaylistsCache(): Flow<List<PlaylistDto>> =
        playlistCacheDao.getPlaylistsCache().map {list->
            list.map { it.toDto() }
        }
    suspend fun upsertGenresCache(genresListCache:List<GenreDto>){
        genreCacheDao.upsertGenresCache(genresListCache.map {
            it.toGenreCache()
        })
    }
    suspend fun deleteGenresCache(){
        genreCacheDao.deleteGenresCache()
    }

    fun getGenresCache(): Flow<List<GenreDto>> =
        genreCacheDao.getGenresCache().map { list->
            list.map { it.toDto() }
        }
    suspend fun upsertArtistsCache(artistListCache:List<ArtistDto>,typeOfCache: Int){
       artistCacheDao.upsertArtistsCache(artistListCache.map {
           it.toArtistCache(typeOfCache)
       })
    }

    suspend fun deleteArtistsCache(typeOfCache: Int){
        artistCacheDao.deleteArtistsCache(typeOfCache)
    }

    fun getArtistsCache(typeOfCache: Int): Flow<List<ArtistDto>> =
        artistCacheDao.getArtistsCache(typeOfCache).map { list->
            list.map { it.toDto() }
        }

    suspend fun upsertAlbumsCache(albumListCache:List<AlbumDto>,typeOfCache: Int){
        albumCacheDao.upsertAlbumsCache(
            albumListCache.map{
                it.toAlbumCache(typeOfCache)
            }
        )
    }
    suspend fun deleteAlbumsCache(typeOfCache: Int){
        albumCacheDao.deleteAlbumsCache(typeOfCache)
    }

     fun getAlbumsCache(typeOfCache: Int): Flow<List<AlbumDto>> =
        albumCacheDao.getAlbumsCache(typeOfCache).map { list->
            list.map { it.toDto() }
        }


    suspend fun addTrackToFavourites(track: TrackDto){
        trackDao.upsertTrack(track.toFavouriteTrack())
    }

    suspend fun deleteTrackFromFavourites(track: TrackDto){
        trackDao.deleteTrack(track.id)
    }

    fun getFavouritesTracks(): Flow<List<TrackDto>> {
        return trackDao.getAllFavouriteTracks().map {
            it.map { favouriteTrack -> favouriteTrack.toDto()  }
        }
    }


    suspend fun trackIsAlreadyAFavourite(track: TrackDto):Boolean{
        return trackDao.favTrackCount(track.id) != 0
    }

    suspend fun thereIsAlbumCacheInfo(typeOfCache: Int):Boolean{
        return albumCacheDao.albumCacheCount(typeOfCache) != 0
    }

    suspend fun thereIsArtistCacheInfo(typeOfCache: Int):Boolean{
        return artistCacheDao.artistCacheCount(typeOfCache) != 0
    }
    suspend fun thereIsGenreCacheInfo():Boolean{
        return genreCacheDao.genreCacheCount()!=0
    }

    suspend fun thereIsPlaylistCacheInfo():Boolean{
        return playlistCacheDao.playlistCacheCount() != 0
    }

    suspend fun thereIsTracksCacheInfo(typeOfCache: Int):Boolean{
        return trackCacheDao.trackCacheCount(typeOfCache) != 0
    }

    suspend fun deleteTrackCache(id:Long,typeOfCache: Int){
        trackCacheDao.deleteTrackCache(id,typeOfCache)
    }

    suspend fun deleteAlbumCache(id:Long,typeOfCache: Int){
        albumCacheDao.deleteAlbumCache(id,typeOfCache)
    }

    suspend fun deleteArtistCache(id:Long,typeOfCache: Int){
        artistCacheDao.deleteArtistCache(id,typeOfCache)
    }
}