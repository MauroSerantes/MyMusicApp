package com.myapps.mymusic.ui.tracks

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.FragmentTracksBinding
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.MediaBottomPlayer
import com.myapps.mymusic.ui.player.data.TrackList
import com.myapps.mymusic.ui.player.main.PlayerActivity
import com.myapps.mymusic.ui.tracks.adapters.MainTracksAdapter
import com.myapps.mymusic.utils.getRandomBackgroundColor
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class TracksFragment : Fragment() {

    private var _binding: FragmentTracksBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModels<TracksViewModel>()

    @Inject
    lateinit var  tracksAdapter: MainTracksAdapter

    private val args:TracksFragmentArgs by navArgs()
    private var listOfSongs:List<TrackModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTracksBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        when(args.criterion){
            "artist" -> viewModel.getTracksFromArtist(args.id)
            "album" -> viewModel.getTracksFromAlbum(args.id)
            "playlist"-> viewModel.getTracksFromPlaylist(args.id)
            "radio"-> viewModel.getTracksFromRadios(args.id)
        }

        binding.ivPresentation.setBackgroundColor(getRandomBackgroundColor(requireContext()))
        binding.tvItemName.text = args.tracklistOriginName
        if(args.tracklistCover.isBlank()){
            binding.ivCoverArt.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.no_image_available))
        }
        else{
            Picasso.get().load(args.tracklistCover).into(binding.ivCoverArt)
        }


        binding.backButton.setOnClickListener{
            findNavController().popBackStack()
        }


        binding.rvTracks.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.rvTracks.adapter = tracksAdapter

        lifecycleScope.launch {
            viewModel.uiState.collect{
                when(it){
                    is TracksUiState.Loading->{
                        binding.progressBar.visibility = View.VISIBLE
                        binding.trackContainer.visibility = View.GONE
                    }
                    is TracksUiState.Success->{
                        binding.progressBar.visibility = View.GONE
                        binding.trackContainer.visibility = View.VISIBLE
                        listOfSongs = it.tracks

                        if(args.criterion == "album"){
                            listOfSongs.forEach{tModel->
                                tModel.albumName = args.tracklistOriginName
                                tModel.albumCover = args.tracklistCover
                            }
                        }
                        tracksAdapter.differ.submitList(listOfSongs)
                        configReproduceAllSongs()
                        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.mediaFragment)
                        if(fragment!=null){
                            binding.spacer.visibility = View.VISIBLE
                        }
                    }
                    is TracksUiState.Error->{

                    }
                }
            }
        }

        tracksAdapter.addOnItemClickListener {
            val menu = MusicMenu()
            val bundle = Bundle()
            bundle.putParcelable("track",it)
            menu.arguments = bundle
            menu.show(parentFragmentManager,"menu")
        }

        tracksAdapter.addOnItemClickListener {
            val bundle = Bundle()
            val trackList = TrackList(listOf(it),0)
            bundle.putParcelable("trackList",trackList)
            binding.spacer.visibility = View.VISIBLE
            activity?.supportFragmentManager?.commit {
                add(R.id.mediaFragment,MediaBottomPlayer::class.java,bundle)
            }
        }

    }

    private fun configReproduceAllSongs(){
        binding.reproduceAllButton.setOnClickListener {
            val trackList = TrackList(listOfSongs,0)
            val bundle = Bundle()
            bundle.putParcelable("trackList",trackList)
            binding.spacer.visibility = View.VISIBLE
            activity?.supportFragmentManager?.commit {
                add(R.id.mediaFragment,MediaBottomPlayer::class.java,bundle)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}