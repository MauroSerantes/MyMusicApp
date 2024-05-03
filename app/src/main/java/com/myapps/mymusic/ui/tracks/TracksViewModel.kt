package com.myapps.mymusic.ui.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.mymusic.data.MusicRepository
import com.myapps.mymusic.data.remote.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(private val repository: MusicRepository):ViewModel() {

    private val _uiState = MutableStateFlow<TracksUiState>(TracksUiState.Loading)

    val uiState:StateFlow<TracksUiState> get() = _uiState


    fun getTracksFromAlbum(id:Long){
        viewModelScope.launch{
           repository.getTracksFromAlbum(id).collect{
               if(it.status == Status.SUCCESS){
                   _uiState.value = TracksUiState.Success(it.data!!)
               }
               else{
                   _uiState.value = TracksUiState.Error(it.messageError!!)
               }
           }
        }
    }

    fun getTracksFromArtist(id:Long){
        viewModelScope.launch{
            repository.getTracksOfArtist(id).collect{
                if(it.status == Status.SUCCESS){
                    _uiState.value = TracksUiState.Success(it.data!!)
                }
                else{
                    _uiState.value = TracksUiState.Error(it.messageError!!)
                }
            }
        }
    }

    fun getTracksFromPlaylist(id:Long){
        viewModelScope.launch{
            repository.getTracksFromPlaylist(id).collect{
                if(it.status == Status.SUCCESS){
                    _uiState.value = TracksUiState.Success(it.data!!)
                }
                else{
                    _uiState.value = TracksUiState.Error(it.messageError!!)
                }
            }
        }
    }

    fun getTracksFromRadios(id:Long){
        viewModelScope.launch {
            repository.getTracksFromRadioId(id).collect{
                if(it.status == Status.SUCCESS){
                    _uiState.value = TracksUiState.Success(it.data!!)
                }
                else{
                    _uiState.value = TracksUiState.Error(it.messageError!!)
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