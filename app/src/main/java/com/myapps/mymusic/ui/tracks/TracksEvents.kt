package com.myapps.mymusic.ui.tracks

import com.myapps.mymusic.domain.TrackModel

sealed interface TracksEvents{
    data class UpsertFavTrack(val favTrack:TrackModel): TracksEvents
}