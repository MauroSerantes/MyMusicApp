package com.myapps.mymusic.ui.player.utils

import android.media.MediaMetadataRetriever
import java.io.IOException

fun getMediaDuration(mediaSource: String): Int {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(mediaSource)
        val d = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        var duration = 0
        if (d != null) duration = d.toInt()
        retriever.close()
        duration
    } catch (e: IOException) {
        0
    }
}