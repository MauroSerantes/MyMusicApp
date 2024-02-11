package com.myapps.mymusic.ui.tracks

import com.myapps.mymusic.domain.TrackModel

sealed class TracksUiState{
    data object Loading:TracksUiState()
    data class Success(val tracks:List<TrackModel>):TracksUiState()
    data class Error(val message:String):TracksUiState()
}
