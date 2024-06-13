package com.myapps.mymusic.ui.search.searchingdata

import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.search.model.ResultModel

sealed class SearchingDataState{
    data object Loading:SearchingDataState()
    data class Success(
        val resultsList:List<ResultModel>
    ):SearchingDataState()
    data class Error(val errorMessage:String):SearchingDataState()
}
