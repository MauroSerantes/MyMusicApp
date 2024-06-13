package com.myapps.mymusic.data.remote.model.radio

import com.google.gson.annotations.SerializedName

data class Radio(
    @SerializedName("id")
    val id:Long,
    @SerializedName("title")
    val title:String,
    @SerializedName("picture")
    val picture:String?,
    @SerializedName("picture_xl")
    val pictureXL:String?
)
