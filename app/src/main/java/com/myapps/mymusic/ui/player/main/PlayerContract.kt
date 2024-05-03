package com.myapps.mymusic.ui.player.main

import android.content.Context
import android.content.ServiceConnection
import android.os.Parcelable
import com.myapps.mymusic.data.remote.model.tracks.Track
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.base.BaseContract
import com.myapps.mymusic.ui.player.data.TrackList

interface PlayerContract {
    interface View : BaseContract.View {
       fun showRepeatOnIcon()
       fun showRepeatOffIcon()
       fun showShuffleOnIcon()
       fun showShuffleOffIcon()
       fun showPlayIcon()
       fun showPauseIcon()
       fun updateSongData(track: TrackModel)
       fun updateSongDuration(maxDuration:Int)
    }

    interface Presenter : BaseContract.Presenter<View>,ServiceConnection{
        fun startingService(context: Context,parcelable: Parcelable,action:String?)
        fun bindingService(context: Context)
        fun unbindingService(context: Context)
        fun stopService(context: Context)
        fun onPlayPauseToggle()
        fun onRepeatToggle()
        fun onShuffleToggle()
        fun onNextSong()
        fun onPreviousSong()
        fun onSearchPosition(position:Int)
        fun getCurrentTimeOfPlaying():Int
    }
}