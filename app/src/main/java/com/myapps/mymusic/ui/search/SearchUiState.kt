package com.myapps.mymusic.ui.search

import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.PlaylistModel
import com.myapps.mymusic.domain.TrackModel

sealed class SearchUiState{
    data object Loading:SearchUiState()
    data class Success(
        val topPlaylists:List<PlaylistModel>,
        val searchResults: SearchResults
    ):SearchUiState()
    data class Error(val errorMessage:String):SearchUiState()
}

data class SearchResults(
    val artists:List<ArtistModel> = emptyList(),
    val albums:List<AlbumModel> = emptyList(),
    val tracks:List<TrackModel> = emptyList()
)