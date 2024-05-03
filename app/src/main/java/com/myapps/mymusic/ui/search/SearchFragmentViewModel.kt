package com.myapps.mymusic.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.mymusic.data.MusicRepository
import com.myapps.mymusic.data.remote.DataStatus
import com.myapps.mymusic.data.remote.Status
import com.myapps.mymusic.ui.tracks.TracksEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(private val repository: MusicRepository) : ViewModel() {

    private val genres = repository.getGenres().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), DataStatus.loading(null)
    )

    private val radios = repository.getTopRadios().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(),DataStatus.loading(null)
    )


    val state = combine(genres, radios){genr,rad->
        if(genr.status == Status.SUCCESS && rad.status == Status.SUCCESS){
            SearchUiState.Success(genr.data!!,rad.data!!)
        }
        else{
            if(genr.status == Status.ERROR){
                SearchUiState.Error(genr.messageError!!)
            }
            else{
                if(rad.status == Status.ERROR){
                    SearchUiState.Error(rad.messageError!!)
                } else SearchUiState.Loading
            }
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),SearchUiState.Loading)


 /*
    fun getAlbumsBySearch(albumName:String){
        viewModelScope.launch {
            repository.getAlbumsBySearch(albumName).collect{
                if(it.status == Status.SUCCESS){
                    _uiState.value = SearchUiState.Success(
                        _playlists.value,
                        searchResults = SearchResults(albums = it.data!!)
                    )
                }
                else{
                    _uiState.value = SearchUiState.Error(it.messageError!!)
                }
            }
        }
    }

    fun getArtistsBySearch(artistName:String){
        viewModelScope.launch {
            repository.getArtistsBySearch(artistName).collect{
                if(it.status == Status.SUCCESS){
                    _uiState.value = SearchUiState.Success(
                        _playlists.value,
                        searchResults = SearchResults(artists = it.data!!)
                    )
                }
                else{
                    _uiState.value = SearchUiState.Error(it.messageError!!)
                }
            }
        }
    }

    fun getTracksBySearch(trackName:String){
        viewModelScope.launch {
            repository.getTracksBySearch(trackName).collect{
                if(it.status == Status.SUCCESS){
                    _uiState.value = SearchUiState.Success(
                        _playlists.value,
                        searchResults = SearchResults(tracks = it.data!!)
                    )
                }
                else{
                    _uiState.value = SearchUiState.Error(it.messageError!!)
                }
            }
        }
    }*/

    fun onEvent(events: TracksEvents){
        when(events){
            is TracksEvents.UpsertFavTrack ->{
                viewModelScope.launch {
                    if(!repository.trackIsAlreadyAFavourite(events.favTrack)){
                        repository.addTrackToFavourites(events.favTrack)
                    }
                }

            }
        }
    }

}