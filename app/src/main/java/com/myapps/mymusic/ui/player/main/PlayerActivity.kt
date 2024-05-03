package com.myapps.mymusic.ui.player.main



import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.ActivityPlayer2Binding
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.utils.formattedTime
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Runnable



class PlayerActivity : AppCompatActivity(),PlayerContract.View{

    private lateinit var binding: ActivityPlayer2Binding

    private val presenter = PlayerPresenter()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayer2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter.onAttach(this)
        playButtonConfiguration()
        nextButtonConfiguration()
        previousButtonConfiguration()
        initSeekBar()
        initRepeatAndShuffleButtons()
        configBackButton()
    }

    private fun configBackButton(){
        binding.backButton.setOnClickListener{
            this.finish()
        }
    }

    private fun initRepeatAndShuffleButtons(){
        binding.shuffleButton.setOnClickListener{
           presenter.onShuffleToggle()
        }

        binding.repeatButton.setOnClickListener{
            presenter.onRepeatToggle()
        }

    }

    override fun onStart() {
        super.onStart()
        presenter.bindingService(this)
    }

    private fun initSeekBar(){

        this.runOnUiThread(object :Runnable{
            override fun run() {
                binding.seekBar.progress = presenter.getCurrentTimeOfPlaying()/1000
                binding.durationPlayed.text = formattedTime(presenter.getCurrentTimeOfPlaying()/1000)
                Handler(Looper.getMainLooper()).postDelayed(this,1000)
            }
        } )

        binding.seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    presenter.onSearchPosition(progress * 1000)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun previousButtonConfiguration() {
        binding.prevButton.setOnClickListener {
            presenter.onPreviousSong()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextButtonConfiguration() {
        binding.nextButton.setOnClickListener {
            presenter.onNextSong()
        }

    }

    private fun playButtonConfiguration() {
        binding.playPauseButton.setOnClickListener {
            presenter.onPlayPauseToggle()
        }
    }

    override fun onStop(){
        super.onStop()
        presenter.unbindingService(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun showRepeatOnIcon() {
        binding.repeatButton.setImageResource(R.drawable.repeat_on)
    }

    override fun showRepeatOffIcon() {
        binding.repeatButton.setImageResource(R.drawable.repeat_off)
    }

    override fun showShuffleOnIcon() {
        binding.shuffleButton.setImageResource(R.drawable.shuffle_on)
    }

    override fun showShuffleOffIcon() {
        binding.shuffleButton.setImageResource(R.drawable.repeat_off)
    }

    override fun showPlayIcon() {
        binding.playPauseButton.setImageResource(R.drawable.play)
    }

    override fun showPauseIcon() {
        binding.playPauseButton.setImageResource(R.drawable.pause)
    }

    override fun updateSongData(track: TrackModel) {
        binding.tvAlbumName.text = track.albumName
        binding.tvNameOfTheSong.text = track.title
        binding.tvArtistName.text = track.artistName
        Picasso.get().load(track.albumCover).into(binding.ivCoverArt)
    }

    override fun updateSongDuration(maxDuration: Int) {
        binding.seekBar.max = maxDuration/1000
        binding.durationTotal.text = formattedTime(maxDuration/1000)
    }
}


