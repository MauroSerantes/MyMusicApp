package com.myapps.mymusic.ui.artist

import com.myapps.mymusic.domain.ArtistModel

sealed class ArtistsUiState{
    data object Loading:ArtistsUiState()
    data class Success(val artists:List<ArtistModel>):ArtistsUiState()
    data class Error(val message:String):ArtistsUiState()
}
