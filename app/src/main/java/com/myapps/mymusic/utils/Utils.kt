package com.myapps.mymusic.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat.getParcelableExtra
import com.myapps.mymusic.R

@ColorInt
fun getRandomBackgroundColor(context:Context): Int{
    return when((1..10).random()){
        1-> ContextCompat.getColor(context,R.color.bg_one)
        2->ContextCompat.getColor(context,R.color.bg_two)
        3-> ContextCompat.getColor(context,R.color.bg_three)
        4-> ContextCompat.getColor(context,R.color.bg_four)
        5-> ContextCompat.getColor(context,R.color.bg_five)
        6-> ContextCompat.getColor(context,R.color.bg_six)
        7-> ContextCompat.getColor(context,R.color.bg_seven)
        else-> ContextCompat.getColor(context,R.color.white)
    }
}

fun <T> Bundle.getParcelableExtraCompat(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(name,clazz)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(name) as? T?
    }
}


