package com.myapps.mymusic.ui.player

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.FragmentMediaBottomPlayerBinding
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.data.TrackList
import com.myapps.mymusic.ui.player.main.PlayerActivity
import com.myapps.mymusic.ui.player.main.PlayerContract
import com.myapps.mymusic.ui.player.main.PlayerPresenter
import com.myapps.mymusic.ui.player.utils.ActionConstants.LOAD_TRACK_LIST
import com.myapps.mymusic.utils.getParcelableExtraCompat
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MediaBottomPlayer : Fragment(),PlayerContract.View {

    private var _binding:FragmentMediaBottomPlayerBinding?=null

    private val binding get() = _binding!!

    private val presenter = PlayerPresenter()

    private var seekBarThread:Thread?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onAttach(this)
        val trackList = arguments?.getParcelableExtraCompat("trackList",TrackList::class.java)

        if(trackList!=null){
            presenter.startingService(requireContext(),trackList, LOAD_TRACK_LIST)
        }
    }


    override fun onStart() {
        super.onStart()
        presenter.bindingService(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaBottomPlayerBinding.inflate(layoutInflater,container,false)
        playButtonConfiguration()
        previousButtonConfiguration()
        nextButtonConfiguration()
        initSeekBar()
        binding.root.setOnClickListener {
            val intent = Intent(context, PlayerActivity::class.java)
            requireContext().startActivity(intent)
        }
        return binding.root
    }


    override fun onStop() {
        super.onStop()
        presenter.unbindingService(requireContext())
        seekBarThread?.interrupt()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
        _binding = null
    }

    override fun showRepeatOnIcon() {}

    override fun showRepeatOffIcon() {}

    override fun showShuffleOnIcon() {}

    override fun showShuffleOffIcon() {}

    override fun showPlayIcon() {
        binding.playPauseButton.setImageResource(R.drawable.play)
    }

    override fun showPauseIcon() {
        binding.playPauseButton.setImageResource(R.drawable.pause)
    }

    override fun updateSongData(track: TrackModel) {
        Picasso.get().load(track.albumCover).into(binding.songImage)
        binding.tvSongName.text = track.title
        binding.tvSongArtist.text = track.artistName
    }

    override fun updateSongDuration(maxDuration: Int) {
        binding.seekBar.max = maxDuration/1000
    }



    private fun previousButtonConfiguration() {
        binding.prevButton.setOnClickListener {
            presenter.onPreviousSong()
        }
    }


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

    private fun initSeekBar(){
        seekBarThread = Thread(
            object :Runnable{
                override fun run() {
                    binding.seekBar.progress = presenter.getCurrentTimeOfPlaying()/1000
                    Handler(Looper.getMainLooper()).postDelayed(this,1000)
                }
            }
        )

        seekBarThread?.start()

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
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
}