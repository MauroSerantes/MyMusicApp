package com.myapps.mymusic.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackModel(
    val id:Long,
    val title:String,
    val preview:String,
    val artistName:String,
    var albumName:String,
    var albumCover:String,
):Parcelable
