package com.myapps.mymusic.ui.tracks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.FragmentTracksBinding
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.tracks.adapters.MainTracksAdapter
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

        binding.rvTracks.background = ContextCompat.getDrawable(requireContext(),R.drawable.background_tracks)

        binding.backButton.setOnClickListener{
            findNavController().popBackStack()
        }

        when(args.criterion){
            "artist" -> viewModel.getTracksFromArtist(args.id)
            "album" -> viewModel.getTracksFromAlbum(args.id)
            "playlist"-> viewModel.getTracksFromPlaylist(args.id)
        }

        binding.rvTracks.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.rvTracks.adapter = tracksAdapter

        lifecycleScope.launch {
            viewModel.uiState.collect{
                when(it){
                    is TracksUiState.Loading->{

                    }
                    is TracksUiState.Success->{
                        listOfSongs = it.tracks

                        if(args.criterion == "album"){
                            listOfSongs.forEach{tModel->
                                tModel.albumName = args.albumName!!
                                tModel.albumCover = args.albumCover!!
                            }
                        }
                        tracksAdapter.differ.submitList(listOfSongs)
                    }
                    is TracksUiState.Error->{

                    }
                }
            }
        }

        tracksAdapter.setOnItemClickListener{
            viewModel.onEvent(TracksEvents.UpsertFavTrack(it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}