package com.myapps.mymusic.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.mymusic.databinding.FragmentHomeBinding
import com.myapps.mymusic.ui.home.adapter.GenresAdapter
import com.myapps.mymusic.ui.home.adapter.TopAlbumsAdapter
import com.myapps.mymusic.ui.home.adapter.TopArtistsAdapter
import com.myapps.mymusic.ui.home.adapter.TopTracksAdapter
import com.myapps.mymusic.ui.tracks.TracksEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var genreAdapter: GenresAdapter
    @Inject
    lateinit var artistsAdapter: TopArtistsAdapter
    @Inject
    lateinit var albumsAdapter: TopAlbumsAdapter
    @Inject
    lateinit var tracksAdapter: TopTracksAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  =  FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //genres adapter config
        binding.rvGenresContainer.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.rvGenresContainer.adapter = genreAdapter

        //top tracks adapter config
        binding.rvTopArtistsContainer.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.rvTopArtistsContainer.adapter = artistsAdapter

        //top albums adapter config
        binding.rvTopAlbumsContainer.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.rvTopAlbumsContainer.adapter = albumsAdapter

        //top tracks adapter config
        binding.rvTopTracksContainer.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        binding.rvTopTracksContainer.adapter = tracksAdapter


       lifecycleScope.launch {
           viewModel.uiState.collect{
               when(it){
                   is HomeUiState.Loading->{

                   }
                   is HomeUiState.Success->{
                       genreAdapter.differ.submitList(it.genres)
                       artistsAdapter.differ.submitList(it.artists)
                       albumsAdapter.differ.submitList(it.albums)
                       tracksAdapter.differ.submitList(it.tracks)
                   }
                   is HomeUiState.Error->{

                   }
               }
           }
       }

        // set listeners
        albumsAdapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionHomeFragment2ToTracksFragment(it.id,"album",it.title,it.coverXL)
            findNavController().navigate(direction)
        }

        artistsAdapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionHomeFragment2ToTracksFragment(it.id,"artist",null,null)
            findNavController().navigate(direction)
        }

        genreAdapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionHomeFragment2ToArtistsFragment(it.id)
            findNavController().navigate(direction)
        }

        tracksAdapter.setOnItemClickListener {
            viewModel.onEvent(events = TracksEvents.UpsertFavTrack(favTrack = it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}