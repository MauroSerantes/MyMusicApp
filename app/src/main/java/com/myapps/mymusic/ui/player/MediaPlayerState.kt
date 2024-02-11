package com.myapps.mymusic.ui.player

import com.myapps.mymusic.domain.TrackModel
import java.util.concurrent.atomic.AtomicBoolean

data class MusicPlayerState(
    val currentPosition : Int = 0,
    val trackList: List<TrackModel> = emptyList(),
    val isShuffle: Boolean = false,
    val isRepeat: Boolean = false,
    val isPlaying: Boolean = false,
    val currentSeconds: Int  = 0,
    val currentTrackDuration:Int = 0,
    val completionSong:Boolean = true
)
