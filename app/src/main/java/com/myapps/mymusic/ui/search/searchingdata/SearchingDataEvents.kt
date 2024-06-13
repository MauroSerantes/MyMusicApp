package com.myapps.mymusic.ui.search.searchingdata

import com.myapps.mymusic.ui.search.model.ResultModel

sealed interface SearchingDataEvents{
    data class UpsertSearchCacheInfo(val searchResult:ResultModel):SearchingDataEvents
    data object DeleteAllSearchCacheInfo:SearchingDataEvents
    data class  DeleteSearchCache(val searchResult: ResultModel):SearchingDataEvents
}