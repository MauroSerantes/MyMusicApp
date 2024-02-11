package com.myapps.mymusic.data.remote.model.genre

import com.google.gson.annotations.SerializedName
import com.myapps.mymusic.data.remote.model.genre.Genre

data class GenresResponse(
    @SerializedName("data")
    val data:List<Genre>
)