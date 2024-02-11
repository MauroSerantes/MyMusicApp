package com.myapps.mymusic.data.local

import com.myapps.mymusic.core.mappers.toDto
import com.myapps.mymusic.core.mappers.toFavouriteTrack
import com.myapps.mymusic.data.local.service.TracksDao
import com.myapps.mymusic.data.model.TrackDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalSource @Inject constructor(private val trackDao: TracksDao){

    suspend fun addTrackToFavourites(track: TrackDto){
        trackDao.upsertTrack(track.toFavouriteTrack())
    }

    suspend fun deleteTrackFromFavourites(track: TrackDto){
        trackDao.deleteTrack(
            track.title,
            track.preview,
            track.artistName,
            track.albumName,
            track.albumCover
        )
    }

    fun getFavouritesTracks(): Flow<List<TrackDto>> {
        return trackDao.getAllFavouriteTracks().map {
            it.map { favouriteTrack -> favouriteTrack.toDto()  }
        }
    }


    suspend fun trackIsAlreadyAFavourite(track: TrackDto):Boolean{
        return trackDao.favTrackCount(
            track.title,
            track.preview,
            track.artistName,
            track.albumName,
            track.albumCover
        ) != 0
    }
}