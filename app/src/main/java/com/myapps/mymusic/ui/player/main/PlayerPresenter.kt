package com.myapps.mymusic.ui.player.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Parcelable
import com.myapps.mymusic.ui.player.service.MediaPlayerService
import com.myapps.mymusic.ui.player.base.BasePresenter
import com.myapps.mymusic.ui.player.utils.ActionConstants.LOAD_TRACK_LIST
import com.myapps.mymusic.ui.player.utils.getMediaDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerPresenter: BasePresenter<PlayerContract.View>() , PlayerContract.Presenter{

    private var musicService: MediaPlayerService?= null
    private var serviceBounded = false

    override fun startingService(context: Context, parcelable: Parcelable, action:String?) {
        val intent = Intent(context,MediaPlayerService::class.java)
        intent.action = action
        when(action!!){
            LOAD_TRACK_LIST->{
                intent.putExtra("serviceTrackList",parcelable)
            }
            else->{}
        }
        context.startService(intent)
    }


    override fun bindingService(context: Context) {
        context.bindService(Intent(context, MediaPlayerService::class.java),this,Context.BIND_AUTO_CREATE)
    }


    override fun unbindingService(context: Context) {
        context.unbindService(this)
    }

    override fun stopService(context: Context) {
        context.stopService(Intent(context, MediaPlayerService::class.java))
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
        view?.updateSongDuration(getMediaDuration(musicService?.state?.value?.trackList?.get(musicService?.state?.value?.currentPosition!!)!!.preview))


        CoroutineScope(Dispatchers.Main).launch {
            musicService?.state?.collect{ musicState ->

                if(musicState.isPlaying){
                    view?.showPauseIcon()
                }
                else{
                    view?.showPlayIcon()
                }

                if(musicState.completionSong){
                    view?.updateSongData(musicService?.state?.value?.trackList?.get(musicService?.state?.value?.currentPosition!!)!!)
                    view?.showPauseIcon()
                }

            }
        }

    }

    override fun onServiceDisconnected(name: ComponentName?) {
        serviceBounded = false
        musicService = null
    }
}