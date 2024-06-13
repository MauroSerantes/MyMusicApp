package com.myapps.mymusic.data.remote.model.radio

import com.google.gson.annotations.SerializedName


data class RadioResponse(
    @SerializedName("data")
    val data:List<Radio>
)