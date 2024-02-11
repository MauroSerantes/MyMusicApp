package com.myapps.mymusic.ui.player.data

import android.os.Parcelable
import com.myapps.mymusic.domain.TrackModel
import kotlinx.parcelize.Parcelize


@Parcelize
data class TrackList(
    val trackList: List<TrackModel>,
    val initPosition:Int,
) : Parcelable
