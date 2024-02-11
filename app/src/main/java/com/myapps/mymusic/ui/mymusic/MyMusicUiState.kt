package com.myapps.mymusic.ui.mymusic

import com.myapps.mymusic.domain.TrackModel

sealed class MyMusicUiState {
    data object Loading:MyMusicUiState()
    data class Success(val tracks:List<TrackModel>):MyMusicUiState()
    data class Error(val errorMessage:String):MyMusicUiState()
}
