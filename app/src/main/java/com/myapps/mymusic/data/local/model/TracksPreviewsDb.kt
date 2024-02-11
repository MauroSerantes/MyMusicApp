package com.myapps.mymusic.data.local.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myapps.mymusic.data.local.service.TracksDao

@Database(entities = [FavouriteTrack::class], version = 3, exportSchema = true)
abstract class TracksPreviewsDb:RoomDatabase(){
    abstract fun tracksDao(): TracksDao
}