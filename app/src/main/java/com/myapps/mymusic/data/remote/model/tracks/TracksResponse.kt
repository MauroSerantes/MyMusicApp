package com.myapps.mymusic.data.remote.model.tracks

import com.google.gson.annotations.SerializedName

data class TracksResponse(
    @SerializedName("data")
    val data:List<Track>,
)

