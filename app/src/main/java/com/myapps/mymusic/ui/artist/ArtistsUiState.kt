package com.myapps.mymusic.ui.artist

import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.RadioModel
import org.w3c.dom.ls.LSException

sealed class ArtistsUiState{
    data object Loading:ArtistsUiState()
    data class Success(
        val artists:List<ArtistModel>
    ):ArtistsUiState()
    data class Error(val message:String):ArtistsUiState()
}
