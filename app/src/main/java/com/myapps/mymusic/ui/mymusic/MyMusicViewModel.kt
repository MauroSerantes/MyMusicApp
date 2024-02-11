package com.myapps.mymusic.ui.mymusic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.mymusic.data.MusicRepository
import com.myapps.mymusic.data.remote.Status
import com.myapps.mymusic.domain.TrackModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMusicViewModel @Inject constructor(private val repository: MusicRepository):ViewModel() {

    private val _uiState = MutableStateFlow<MyMusicUiState>(MyMusicUiState.Loading)

    val uiState:StateFlow<MyMusicUiState> get() = _uiState

    init {
        getFavouriteTracks()
    }

     private fun getFavouriteTracks(){
        viewModelScope.launch {
            repository.getFavouritesTracks().collect{
                if(it.status == Status.SUCCESS){
                    _uiState.value = MyMusicUiState.Success(it.data!!)
                }
                else{
                    _uiState.value = MyMusicUiState.Error(it.messageError!!)
                }
            }
        }
    }

    fun deleteSong(trackModel: TrackModel){
        viewModelScope.launch {
           repository.deleteTrackFromFavourites(trackModel)
        }
        getFavouriteTracks()
    }
}