package com.myapps.mymusic.ui.player.main

import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.os.postDelayed
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.service.MediaPlayerService
import com.myapps.mymusic.ui.player.base.BasePresenter
import com.myapps.mymusic.ui.player.data.TrackList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext

class PlayerPresenter: BasePresenter<PlayerContract.View>() , PlayerContract.Presenter{

    private var musicService: MediaPlayerService?= null
    private var serviceBounded = false

    private var job:Job?=null

    override fun startingService(context: Context, trackList: TrackList) {
        val intent = Intent(context,MediaPlayerService::class.java)
        intent.putExtra("serviceTrackList",trackList)
        context.startService(intent)
    }


    override fun bindingService(context: Context) {
        context.bindService(Intent(context, MediaPlayerService::class.java),this,Context.BIND_AUTO_CREATE)
        job = CoroutineScope(Dispatchers.IO).launch {
            while (true){
                if(musicService?.state?.value?.completionSong == true){
                    runBlocking(Dispatchers.Main){
                        view?.updateSongData(musicService?.state?.value?.trackList?.get(musicService?.state?.value?.currentPosition!!)!!)
                        view?.showPauseIcon()
                    }
                }
            }
        }
    }



    override fun unbindingService(context: Context) {
        job?.cancel()
        context.unbindService(this)
        job = null
    }

    override fun stopService(context: Context) {
        job?.cancel()
        context.stopService(Intent(context, MediaPlayerService::class.java))
        job = null
    }

    override fun onPlayPauseToggle() {
        if(serviceBounded){
            if(musicService?.state?.value?.isPlaying == true){
                musicService?.pauseMusic()
                view?.showPlayIcon()
            }
            else{
                musicService?.startMusic()
                view?.showPauseIcon()
            }
        }
    }

    override fun onRepeatToggle(){
        if(serviceBounded){
            musicService?.updateRepeatState()

            if(musicService?.state?.value?.isRepeat == true){
                view?.showRepeatOnIcon()
            }
            else{
                view?.showRepeatOffIcon()
            }

        }
    }

    override fun onShuffleToggle() {
        if(serviceBounded){
            musicService?.updateShuffleState()

            if(musicService?.state?.value?.isShuffle == true){
                view?.showShuffleOnIcon()
            }
            else{
                view?.showShuffleOffIcon()
            }

        }
    }

    override fun onNextSong() {
        if(!serviceBounded) return

        if(musicService?.state?.value?.isPlaying == true) musicService?.stopMusic()

        if(musicService?.state?.value?.isShuffle == true && musicService?.state?.value?.isRepeat == false){
            musicService?.updateTrackPosition((musicService?.state?.value?.trackList?.indices)?.random()!!)
        }

        else if(musicService?.state?.value?.isShuffle == false && musicService?.state?.value?.isRepeat == false){

            musicService?.updateTrackPosition(
                ((musicService?.state?.value?.currentPosition?.plus(1))?.rem(musicService?.state?.value?.trackList?.size!!)!!)
            )
        }

        musicService?.loadSong(musicService?.state?.value?.currentPosition!!)

        view?.updateSongData(musicService?.state?.value?.trackList?.get(musicService?.state?.value?.currentPosition!!)!!)
        view?.updateSongDuration(musicService?.state?.value?.currentTrackDuration!!)
    }

    override fun onPreviousSong() {
        if(!serviceBounded) return


        if(musicService?.state?.value?.isPlaying == true) musicService?.stopMusic()

        if(musicService?.state?.value?.isShuffle == true && musicService?.state?.value?.isRepeat == false){
            musicService?.updateTrackPosition((musicService?.state?.value?.trackList?.indices)?.random()!!)
        }
        else if(musicService?.state?.value?.isShuffle == false && musicService?.state?.value?.isRepeat == false){

            if(musicService?.state?.value?.currentPosition!! - 1 < 0){
                musicService?.updateTrackPosition(musicService?.state?.value?.trackList?.size!! - 1)
            }
            else{
                musicService?.updateTrackPosition(musicService?.state?.value?.currentPosition!! - 1)
            }
        }

        musicService?.loadSong(musicService?.state?.value?.currentPosition!!)

        view?.updateSongData(musicService?.state?.value?.trackList?.get(musicService?.state?.value?.currentPosition!!)!!)
        view?.updateSongDuration(musicService?.state?.value?.currentTrackDuration!!)
    }

    override fun onSearchPosition(position: Int) {
        if(serviceBounded){
            musicService?.seekTo(position)
        }
    }

    override fun getCurrentTimeOfPlaying(): Int {
        if(!serviceBounded) return 0

        return musicService?.state?.value?.currentSeconds!!
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as (MediaPlayerService.MusicBinder)
        musicService = binder.getService()
        serviceBounded = true

        view?.updateSongData(musicService?.state?.value?.trackList?.get(musicService?.state?.value?.currentPosition!!)!!)
        view?.showPauseIcon()

        Handler(Looper.getMainLooper()).postDelayed({
            view?.updateSongDuration(musicService?.state?.value?.currentTrackDuration!!)
        }, 2000)

    }

    override fun onServiceDisconnected(name: ComponentName?) {
        serviceBounded = false
        musicService = null
    }
}