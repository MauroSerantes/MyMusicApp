package com.myapps.mymusic.ui.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.mymusic.data.MusicRepository
import com.myapps.mymusic.data.remote.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(private val repository: MusicRepository):ViewModel() {

    private val _uiState = MutableStateFlow<ArtistsUiState>(ArtistsUiState.Loading)

    val uiState: StateFlow<ArtistsUiState> get() = _uiState

    fun getArtistByMusicalGenre(id:Long){
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

