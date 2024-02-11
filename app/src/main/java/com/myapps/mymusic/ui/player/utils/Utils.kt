package com.myapps.mymusic.ui.player.utils

import android.content.Intent
import android.os.Build

fun formattedTime( currentPosition:Int):String{
    val seconds = (currentPosition%60).toString()
    val minutes = (currentPosition/60).toString()
    val totalOut  = "$minutes:$seconds"
    val totalNew = "$minutes:0$seconds"

    return if(seconds.length == 1) totalNew
    else totalOut
}

fun <T> Intent.getParcelableExtraCompat(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(name, clazz)
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(name) as? T?
    }
}
