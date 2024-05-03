package com.myapps.mymusic.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.ScrollState
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.FragmentHomeBinding
import com.myapps.mymusic.ui.search.adapter.GenresAdapter
import com.myapps.mymusic.ui.home.adapter.TopAlbumsAdapter
import com.myapps.mymusic.ui.home.adapter.TopArtistsAdapter
import com.myapps.mymusic.ui.home.adapter.TopPlaylistsAdapter
import com.myapps.mymusic.ui.home.adapter.TopTracksAdapter
import com.myapps.mymusic.ui.player.MediaBottomPlayer
import com.myapps.mymusic.ui.player.data.TrackList
import com.myapps.mymusic.ui.tracks.TracksEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var playlistsAdapter: TopPlaylistsAdapter
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
        binding.rvTopPlaylistContainer.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.rvTopPlaylistContainer.adapter = playlistsAdapter

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

        lifecycleScope.launch() {
           viewModel.uiState.collect{
               when(it){
                   is HomeUiState.Loading->{
                       binding.progressBar.visibility = View.VISIBLE
                       binding.scroll.visibility = View.GONE
                   }
                   is HomeUiState.Success->{
                       binding.progressBar.visibility = View.GONE
                       binding.scroll.visibility = View.VISIBLE
                       playlistsAdapter.differ.submitList(it.chartData.topPlaylists)
                       artistsAdapter.differ.submitList(it.chartData.topArtists)
                       albumsAdapter.differ.submitList(it.chartData.topAlbums)
                       tracksAdapter.differ.submitList(it.chartData.topTracks)

                       val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.mediaFragment)
                       if(fragment!=null){
                           binding.spacer.visibility = View.VISIBLE
                       }
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
            val direction = HomeFragmentDirections.actionHomeFragment2ToTracksFragment(it.id,"artist",it.name,it.pictureXL)
            findNavController().navigate(direction)
        }

        playlistsAdapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionHomeFragment2ToTracksFragment(it.id,"playlist",it.title,it.pictureXL)
            findNavController().navigate(direction)
        }

        tracksAdapter.addOnItemClickListener {
            val bundle = Bundle()
            val trackList = TrackList(listOf(it),0)
            bundle.putParcelable("trackList",trackList)
            activity?.supportFragmentManager?.commit {
                add(R.id.mediaFragment, MediaBottomPlayer::class.java,bundle)
            }
            binding.spacer.visibility = View.VISIBLE
        }


        tracksAdapter.addOnItemClickListener {
            viewModel.onEvent(events = TracksEvents.UpsertFavTrack(favTrack = it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}