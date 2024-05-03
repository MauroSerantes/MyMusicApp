package com.myapps.mymusic.ui.home

import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.ChartModel
import com.myapps.mymusic.domain.GenreModel
import com.myapps.mymusic.domain.TrackModel


sealed class HomeUiState{
    data object Loading:HomeUiState()
    data class Success(
        val chartData:ChartModel
    ):HomeUiState()
    data class Error(val errorMessage:String):HomeUiState()
}

