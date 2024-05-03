package com.myapps.mymusic.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.mymusic.data.MusicRepository
import com.myapps.mymusic.data.remote.Status
import com.myapps.mymusic.domain.emptyChart
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

    init {
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch {
            repository.getChart().collect{
                if(it.status == Status.SUCCESS){
                    _uiState.value = HomeUiState.Success(it.data!!)
                }
                if(it.status == Status.ERROR){
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