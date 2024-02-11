package com.myapps.mymusic.data.remote.model.artists

import com.google.gson.annotations.SerializedName

data class ArtistsResponse(
    @SerializedName("data")
    val data:List<Artist>,
)



