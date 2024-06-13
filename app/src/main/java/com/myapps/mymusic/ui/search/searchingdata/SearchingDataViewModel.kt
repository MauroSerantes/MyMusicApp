package com.myapps.mymusic.ui.search.searchingdata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.mymusic.core.mappers.toAlbumModel
import com.myapps.mymusic.core.mappers.toArtistModel
import com.myapps.mymusic.core.mappers.toResult
import com.myapps.mymusic.core.mappers.toTrackModel
import com.myapps.mymusic.data.MusicRepository
import com.myapps.mymusic.data.local.model.TypeOfAlbumCache
import com.myapps.mymusic.data.local.model.TypeOfTrackCache
import com.myapps.mymusic.data.remote.DataStatus
import com.myapps.mymusic.data.remote.Status
import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.search.model.ResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchingDataViewModel @Inject constructor(private val repository: MusicRepository):ViewModel() {

    private val query = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _artists = query.flatMapLatest {
        repository.getArtistsBySearch(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),DataStatus.loading())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _albums = query.flatMapLatest {
        repository.getAlbumsBySearch(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),DataStatus.loading())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _tracks = query.flatMapLatest {
        repository.getTracksBySearch(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),DataStatus.loading())


    private val _artistCache = repository.getSearchArtistsCache().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )
    private val _albumsCache = repository.getSearchAlbumCache().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )
    private val _tracksCache = repository.getSearchTracksCache().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )

    val searchCache = combine(_tracksCache,_albumsCache,_artistCache){
        tracks,albums,artists->
        searchResultsMerge(artists,tracks,albums)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())



    val uiState = combine(_artists,_albums,_tracks){ artist,albums,tracks->
        if(artist.status == Status.SUCCESS &&
            albums.status == Status.SUCCESS &&
            tracks.status == Status.SUCCESS){
            SearchingDataState.Success(searchResultsMerge(artist.data!!,tracks.data!!,albums.data!!))
        }
        else{
            if(albums.status == Status.ERROR){
                SearchingDataState.Error(albums.messageError!!)
            }
            else if(artist.status == Status.ERROR){
                SearchingDataState.Error(artist.messageError!!)
            }
            else if(tracks.status == Status.ERROR){
                SearchingDataState.Error(tracks.messageError!!)
            }
            else  SearchingDataState.Loading
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),SearchingDataState.Loading)


    fun updateQueryState(queryVal:String){
        query.value = queryVal
    }

    fun getCurrentQuery():String = query.value


    fun onEvent(event:SearchingDataEvents){
        when(event){
            is SearchingDataEvents.UpsertSearchCacheInfo->{
                viewModelScope.launch{
                    when(event.searchResult.type){
                        "album"->{
                            repository.upsertAlbumSearchCache(event.searchResult.toAlbumModel())
                        }
                        "track"->{
                            repository.upsertTrackSearchCache(event.searchResult.toTrackModel())
                        }
                        "artist"->{
                            repository.upsertArtistSearchCache(event.searchResult.toArtistModel())
                        }
                    }
                }
            }
            is SearchingDataEvents.DeleteAllSearchCacheInfo->{
                viewModelScope.launch{
                    repository.deleteAllArtistSearchCache(TypeOfTrackCache.SEARCH_TRACK)
                    repository.deleteAllAlbumSearchCache(TypeOfAlbumCache.SEARCH_ALBUM)
                    repository.deleteAllTrackSearchCache(TypeOfTrackCache.SEARCH_TRACK)
                }

            }
            is SearchingDataEvents.DeleteSearchCache->{
                viewModelScope.launch {
                    when(event.searchResult.type){
                        "album"->{
                            repository.deleteAlbumSearchCache(event.searchResult.toAlbumModel())
                        }
                        "track"->{
                            repository.deleteTrackSearchCache(event.searchResult.toTrackModel())
                        }
                        "artist"->{
                            repository.deleteArtistSearchCache(event.searchResult.toArtistModel())
                        }
                    }
                }
            }
        }
    }

    private fun searchResultsMerge(
        artists:List<ArtistModel>,
        tracks:List<TrackModel>,
        albums:List<AlbumModel>
    ):MutableList<ResultModel>{
        val results = ArrayList<ResultModel>()

        artists.forEach{
            results.add(it.toResult())
        }
        tracks.forEach{
            results.add(it.toResult())
        }
        albums.forEach{
            results.add(it.toResult())
        }
        return results
    }
}