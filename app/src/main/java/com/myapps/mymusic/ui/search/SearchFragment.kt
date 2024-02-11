package com.myapps.mymusic.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.FragmentSearchBinding
import com.myapps.mymusic.ui.tracks.TracksEvents
import com.myapps.mymusic.ui.search.adapter.AlbumsResultAdapter
import com.myapps.mymusic.ui.search.adapter.ArtistsResultAdapter
import com.myapps.mymusic.ui.search.adapter.TopPlaylistsAdapter
import com.myapps.mymusic.ui.search.adapter.TracksResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding:FragmentSearchBinding ?= null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchFragmentViewModel>()

    @Inject
    lateinit var playlistsAdapter:TopPlaylistsAdapter
    @Inject
    lateinit var trackResponseAdapter:TracksResultAdapter
    @Inject
    lateinit var albumResponseAdapter:AlbumsResultAdapter
    @Inject
    lateinit var artistResponseAdapter:ArtistsResultAdapter

    private var showSelection = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvSearchResults.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        // init radio buttons functionalities
        binding.searchBar.queryHint = getString(R.string.search_by_artist)

        binding.artistButton.setOnClickListener{
           binding.searchBar.queryHint = getString(R.string.search_by_artist)
        }

        binding.albumButton.setOnClickListener{
            binding.searchBar.queryHint = getString(R.string.search_by_album)
        }

        binding.trackButton.setOnClickListener{
            binding.searchBar.queryHint = getString(R.string.search_by_track)
        }


        // data of playlists

        binding.rvSearchView.layoutManager = GridLayoutManager(requireContext(),2).apply {
            this.orientation = GridLayoutManager.HORIZONTAL
        }
        binding.rvSearchView.adapter = playlistsAdapter


        lifecycleScope.launch{
            viewModel.uiState.collect{
                when(it){
                    is SearchUiState.Loading->{

                    }
                    is SearchUiState.Success->{
                        playlistsAdapter.differ.submitList(it.topPlaylists)
                    }

                    is SearchUiState.Error->{

                    }
                }
            }
        }

        binding.selectButton.setOnClickListener{
            showSelection = !showSelection
            if(showSelection){
                binding.radioGroupSelection.visibility = View.VISIBLE
            }
            else{
                binding.radioGroupSelection.visibility = View.GONE
            }
        }

        playlistsAdapter.setOnItemClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToTracksFragment(it.id,"playlist",null,null)
            findNavController().navigate(direction)
        }


        binding.searchBar.setOnQueryTextListener(object :OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(binding.albumButton.isChecked){
                    binding.resultsContainer.visibility = View.VISIBLE
                    viewModel.getAlbumsBySearch(query!!)

                    binding.rvSearchResults.adapter = albumResponseAdapter

                    lifecycleScope.launch {
                        viewModel.uiState.collect{
                            when(it){
                                is SearchUiState.Loading->{

                                }
                                is SearchUiState.Success->{
                                    albumResponseAdapter.differ.submitList(emptyList())
                                    albumResponseAdapter.differ.submitList(it.searchResults.albums)
                                }

                                is SearchUiState.Error->{

                                }
                            }
                        }
                    }
                    binding.searchBar.clearFocus()
                    return true
                }

                if(binding.artistButton.isChecked){
                    binding.resultsContainer.visibility = View.VISIBLE
                    viewModel.getArtistsBySearch(query!!)

                    binding.rvSearchResults.adapter = artistResponseAdapter

                    lifecycleScope.launch {
                        viewModel.uiState.collect{
                            when(it){
                                is SearchUiState.Loading->{

                                }
                                is SearchUiState.Success->{
                                    artistResponseAdapter.differ.submitList(emptyList())
                                    artistResponseAdapter.differ.submitList(it.searchResults.artists)
                                }

                                is SearchUiState.Error->{

                                }
                            }
                        }
                    }
                    binding.searchBar.clearFocus()
                    return true
                }

                if(binding.trackButton.isChecked){
                    binding.resultsContainer.visibility = View.VISIBLE
                    viewModel.getTracksBySearch(query!!)

                    binding.rvSearchResults.adapter = trackResponseAdapter

                    lifecycleScope.launch {
                        viewModel.uiState.collect{
                            when(it){
                                is SearchUiState.Loading->{

                                }
                                is SearchUiState.Success->{
                                    trackResponseAdapter.differ.submitList(emptyList())
                                    trackResponseAdapter.differ.submitList(it.searchResults.tracks)
                                }

                                is SearchUiState.Error->{

                                }
                            }
                        }
                    }
                    binding.searchBar.clearFocus()
                    return true
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.resultsContainer.visibility = View.GONE
                binding.radioGroupSelection.visibility = View.GONE
                return true
            }
        })


        albumResponseAdapter.setOnItemClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToTracksFragment(it.id,"album",it.title,it.coverXL)
            findNavController().navigate(direction)
        }

        artistResponseAdapter.setOnItemClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToTracksFragment(it.id,"artist",null,null)
            findNavController().navigate(direction)
        }

        trackResponseAdapter.setOnItemClickListener {
            viewModel.onEvent(TracksEvents.UpsertFavTrack(it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}