package com.myapps.mymusic.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.mymusic.data.MusicRepository
import com.myapps.mymusic.data.remote.Status
import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.GenreModel
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.tracks.TracksEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MusicRepository):ViewModel(){

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    val uiState:StateFlow<HomeUiState> get() = _uiState

    private val _genres = MutableStateFlow<List<GenreModel>>(emptyList())
    private val _topAlbums = MutableStateFlow<List<AlbumModel>>(emptyList())
    private val _topArtists = MutableStateFlow<List<ArtistModel>>(emptyList())
    private val _topTracks = MutableStateFlow<List<TrackModel>>(emptyList())



    init {
        getGenres()
        getTopArtists()
        getTopTracks()
        getTopAlbums()
    }



    private fun getGenres(){
        viewModelScope.launch{
            repository.getGenres().collect{
                if(it.status == Status.SUCCESS){
                    _genres.value = it.data!!
                    _uiState.value = HomeUiState.Success(
                        genres = _genres.value,
                        artists = _topArtists.value,
                        albums = _topAlbums.value,
                        tracks = _topTracks.value
                    )
                }
                else{
                    _uiState.value = HomeUiState.Error(it.messageError!!)
                }
            }
        }
    }

    private fun getTopArtists(){
        viewModelScope.launch {
            repository.getTopArtists().collect{
                if(it.status == Status.SUCCESS){
                    _topArtists.value = it.data!!
                    _uiState.value = HomeUiState.Success(
                        genres = _genres.value,
                        artists = _topArtists.value,
                        albums = _topAlbums.value,
                        tracks = _topTracks.value
                    )
                }
                else{
                    _uiState.value = HomeUiState.Error(it.messageError!!)
                }
            }
        }
    }

    private fun getTopAlbums(){
        viewModelScope.launch {
            repository.getTopAlbums().collect{
                if(it.status == Status.SUCCESS){
                    _topAlbums.value = it.data!!
                    _uiState.value = HomeUiState.Success(
                        genres = _genres.value,
                        artists = _topArtists.value,
                        albums = _topAlbums.value,
                        tracks = _topTracks.value
                    )
                }
                else{
                    _uiState.value = HomeUiState.Error(it.messageError!!)
                }
            }
        }
    }

    private fun getTopTracks(){
        viewModelScope.launch {
            repository.getTopTracks().collect{
                if(it.status == Status.SUCCESS){
                    _topTracks.value = it.data!!
                    _uiState.value = HomeUiState.Success(
                        genres = _genres.value,
                        artists = _topArtists.value,
                        albums = _topAlbums.value,
                        tracks = _topTracks.value
                    )
                }
                else{
                    _uiState.value = HomeUiState.Error(it.messageError!!)
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