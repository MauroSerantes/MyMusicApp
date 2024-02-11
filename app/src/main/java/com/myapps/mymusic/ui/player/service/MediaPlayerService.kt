package com.myapps.mymusic.ui.player.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.myapps.mymusic.ui.player.MusicPlayerState
import com.myapps.mymusic.ui.player.data.TrackList
import com.myapps.mymusic.ui.player.main.PlayerPresenter
import com.myapps.mymusic.ui.player.utils.getParcelableExtraCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean


class MediaPlayerService :Service(), MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener {

    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var binder: MusicBinder

    private val _state = MutableStateFlow(MusicPlayerState())

    val state:StateFlow<MusicPlayerState> get() = _state

    private var job:Job?=null

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class MusicBinder:Binder(){
        fun getService(): MediaPlayerService = this@MediaPlayerService
    }

    override fun onCreate() {
        super.onCreate()
        binder = MusicBinder()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
                (AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build())
            )
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnCompletionListener(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val trackList:TrackList? = intent?.getParcelableExtraCompat("serviceTrackList",TrackList::class.java)

        _state.update {
            it.copy(
                trackList = trackList?.trackList!!,
                currentPosition =trackList.initPosition
            )
        }
        loadSong(_state.value.currentPosition)

        return super.onStartCommand(intent,flags,startId)
    }

    fun loadSong(position: Int){
        mediaPlayer.apply {
            reset()

            setDataSource(_state.value.trackList[_state.value.currentPosition].preview)

            prepareAsync()
        }
    }

    fun startMusic() {
        mediaPlayer.start()
        _state.update {
            it.copy(isPlaying = true)
        }
    }

    fun stopMusic(){
        job?.cancel()
        mediaPlayer.stop()
        _state.update {
            it.copy(isPlaying = false)
        }
    }

    fun pauseMusic(){
        mediaPlayer.pause()
        _state.update {
            it.copy(isPlaying =  false)
        }
    }

    fun updateRepeatState(){
        _state.update {
            it.copy(isRepeat = !_state.value.isRepeat)
        }
    }

    fun updateShuffleState(){
        _state.update {
            it.copy(isShuffle = !_state.value.isShuffle)
        }
    }

    fun updateTrackPosition(position:Int){
        if(position<0 || position >= _state.value.trackList.size) return

        _state.update {
            it.copy(currentPosition = position)
        }
    }

    fun seekTo(position:Int){
        mediaPlayer.seekTo(position)
        _state.update {
            it.copy(currentSeconds = mediaPlayer.currentPosition)
        }
    }

    private fun updateCurrentTime(){
        job?.cancel()

        job = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                _state.update {
                    it.copy(currentSeconds =  mediaPlayer.currentPosition)
                }
                delay(1000)
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        if(mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.release()
        job = null
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if(mediaPlayer.isPlaying ) mediaPlayer.stop()

        _state.update {
            it.copy(isPlaying = false)
        }

        if(_state.value.isShuffle && !_state.value.isRepeat){
            _state.update {
                it.copy(currentPosition = (state.value.trackList.indices).random())
            }
        }
        else if(!_state.value.isShuffle && !_state.value.isRepeat){
            _state.update {
                it.copy(currentPosition =  (_state.value.currentPosition+1) % _state.value.trackList.size)
            }

        }
        _state.update {
            it.copy(completionSong = true)
        }
        loadSong(_state.value.currentPosition)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()

        _state.update {
            it.copy(
                isPlaying = mediaPlayer.isPlaying,
                currentTrackDuration = mediaPlayer.duration,
                completionSong = false
            )
        }
        updateCurrentTime()
    }
}