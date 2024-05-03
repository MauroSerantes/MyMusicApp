package com.myapps.mymusic.ui.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.mymusic.data.MusicRepository
import com.myapps.mymusic.data.remote.DataStatus
import com.myapps.mymusic.data.remote.Status
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.RadioModel
import com.myapps.mymusic.ui.mymusic.MyMusicUiState
import com.myapps.mymusic.ui.search.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(private val repository: MusicRepository):ViewModel() {


    private val _uiState = MutableStateFlow<ArtistsUiState>(ArtistsUiState.Loading)
    val uiState:StateFlow<ArtistsUiState> get() = _uiState
    fun fetchGenreInformation(id:Long){
        viewModelScope.launch {
            repository.getArtistsByGenre(id).collect{
                if(it.status == Status.SUCCESS){
                    _uiState.value = ArtistsUiState.Success(it.data!!)
                }
                else{
                    _uiState.value = ArtistsUiState.Error(it.messageError!!)
                }
            }
        }
    }
}

