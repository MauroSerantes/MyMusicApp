package com.myapps.mymusic.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.mymusic.data.MusicRepository
import com.myapps.mymusic.data.remote.Status
import com.myapps.mymusic.domain.PlaylistModel
import com.myapps.mymusic.ui.tracks.TracksEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(private val repository: MusicRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)

    val uiState:StateFlow<SearchUiState> get() = _uiState

    private val _playlists = MutableStateFlow<List<PlaylistModel>>(emptyList())



    init {
        getTopPlaylists()
    }

    private fun getTopPlaylists(){
        viewModelScope.launch {
            repository.getTopPlaylists().collect{
                if(it.status == Status.SUCCESS){
                    _playlists.value = it.data!!
                    _uiState.value = SearchUiState.Success(topPlaylists = _playlists.value,
                        searchResults = SearchResults())
                }
                else{
                    _uiState.value = SearchUiState.Error(it.messageError!!)
                }
            }
        }
    }

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
    }

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