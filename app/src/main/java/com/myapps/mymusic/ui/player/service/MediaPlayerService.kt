package com.myapps.mymusic.ui.player.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationManagerCompat
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.MusicPlayerState
import com.myapps.mymusic.ui.player.data.TrackList
import com.myapps.mymusic.ui.player.notification.PlayerNotification
import com.myapps.mymusic.ui.player.utils.ActionConstants.LOAD_TRACK_LIST
import com.myapps.mymusic.ui.player.utils.ActionConstants.NEXT
import com.myapps.mymusic.ui.player.utils.ActionConstants.PLAY_PAUSE
import com.myapps.mymusic.ui.player.utils.ActionConstants.PREV
import com.myapps.mymusic.ui.player.utils.getParcelableExtraCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MediaPlayerService :Service(), MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener {

    private lateinit var mediaPlayer:MediaPlayer

    private lateinit var binder: MusicBinder

    private lateinit var playerNotification:PlayerNotification

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
        playerNotification = PlayerNotification()
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
        intent?.let {
            when(it.action){
                LOAD_TRACK_LIST->{
                    val trackList:TrackList? = it.getParcelableExtraCompat("serviceTrackList",TrackList::class.java)

                    _state.update { player->
                        player.copy(
                            trackList = trackList?.trackList!!,
                            currentPosition =trackList.initPosition
                        )
                    }
                    loadSong(_state.value.currentPosition)
                }
                PLAY_PAUSE->{
                    if(_state.value.isPlaying){
                        pauseMusic()
                    }
                    else{
                        startMusic()
                    }
                }
                PREV->{
                    if(state.value.isPlaying) stopMusic()

                    if(state.value.isShuffle && !state.value.isRepeat){
                       updateTrackPosition(state.value.trackList.indices.random())
                    }
                    else if(!state.value.isShuffle && !state.value.isRepeat){

                        if(state.value.currentPosition - 1 < 0){
                            updateTrackPosition(state.value.trackList.size - 1)
                        }
                        else{
                            updateTrackPosition(state.value.currentPosition - 1)
                        }
                    }
                    loadSong(state.value.currentPosition)
                }
                NEXT->{
                    if(state.value.isPlaying) stopMusic()

                    if(state.value.isShuffle && !state.value.isRepeat){
                       updateTrackPosition((state.value.trackList.indices).random())
                    }

                    else if(!state.value.isShuffle && !state.value.isRepeat){

                        updateTrackPosition(
                            ((state.value.currentPosition.plus(1)).rem(state.value.trackList.size))
                        )
                    }
                    loadSong(state.value.currentPosition)
                }
                else->{}
            }
        }

       return super.onStartCommand(intent, flags, startId)
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
        startService(_state.value.trackList[_state.value.currentPosition])
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
        startService(_state.value.trackList[_state.value.currentPosition])
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
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.cancel(1)
        job?.cancel()
        if(mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.release()
        job = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }


    override fun onCompletion(mp: MediaPlayer?) {
        if(mediaPlayer.isPlaying) mediaPlayer.stop()

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
                isPlaying = true,
                currentTrackDuration = mediaPlayer.duration,
                completionSong = false
            )
        }
        updateCurrentTime()
        startService(_state.value.trackList[_state.value.currentPosition])
    }


    private fun startService(trackModel: TrackModel) {
        val notificationManager = NotificationManagerCompat.from(this)

        val notification = playerNotification.createNotification(
            this,
            trackModel,
            notificationManager,
            _state.value.isPlaying
        )

        startForeground(1,notification)
    }

}