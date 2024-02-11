package com.myapps.mymusic.ui.home

import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.GenreModel
import com.myapps.mymusic.domain.TrackModel


sealed class HomeUiState{
    data object Loading:HomeUiState()
    data class Success(
        val genres:List<GenreModel>,
        val artists:List<ArtistModel>,
        val albums:List<AlbumModel>,
        val tracks:List<TrackModel>
    ):HomeUiState()
    data class Error(val errorMessage:String):HomeUiState()
}

