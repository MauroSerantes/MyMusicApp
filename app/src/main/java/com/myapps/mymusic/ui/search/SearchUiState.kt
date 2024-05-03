package com.myapps.mymusic.ui.search

import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.GenreModel
import com.myapps.mymusic.domain.PlaylistModel
import com.myapps.mymusic.domain.RadioModel
import com.myapps.mymusic.domain.TrackModel

sealed class SearchUiState{
    data object Loading:SearchUiState()
    data class Success(
        val genres:List<GenreModel>,
        val topRadios:List<RadioModel>
    ):SearchUiState()
    data class Error(val errorMessage:String):SearchUiState()
}



/*
data class SearchResults(
    val artists:List<ArtistModel> = emptyList(),
    val albums:List<AlbumModel> = emptyList(),
    val tracks:List<TrackModel> = emptyList()
)*/