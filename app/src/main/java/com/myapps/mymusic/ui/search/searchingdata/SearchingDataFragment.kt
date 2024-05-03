package com.myapps.mymusic.ui.search.searchingdata

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.ViewUtils
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.mymusic.R
import com.myapps.mymusic.core.mappers.toResult
import com.myapps.mymusic.core.mappers.toTrackModel
import com.myapps.mymusic.databinding.FragmentSearchBinding
import com.myapps.mymusic.databinding.FragmentSearchingDataBinding
import com.myapps.mymusic.domain.AlbumModel
import com.myapps.mymusic.domain.ArtistModel
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.MediaBottomPlayer
import com.myapps.mymusic.ui.player.data.TrackList
import com.myapps.mymusic.ui.player.main.PlayerActivity
import com.myapps.mymusic.ui.search.SearchFragmentViewModel
import com.myapps.mymusic.ui.search.SearchUiState
import com.myapps.mymusic.ui.search.adapter.LatestSearchesAdapter
import com.myapps.mymusic.ui.search.adapter.ResultAdapter
import com.myapps.mymusic.ui.search.model.ResultModel
import com.myapps.mymusic.ui.tracks.MusicMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SearchingDataFragment : Fragment() {

    private var _binding:FragmentSearchingDataBinding?= null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchingDataViewModel>()
    @Inject
    lateinit var resultsAdapter:ResultAdapter
    @Inject
    lateinit var latestSearchesAdapter:LatestSearchesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchingDataBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvLatestSearches.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.rvLatestSearches.adapter = latestSearchesAdapter


        binding.rvSearchResults.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.rvSearchResults.adapter = resultsAdapter


        lifecycleScope.launch {
            viewModel.searchCache.collect{
                latestSearchesAdapter.differ.submitList(it)
                if(viewModel.getCurrentQuery().isBlank()){
                    showSearchCache(it)
                }
                else{
                    showResultsInfo()
                }
                val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.mediaFragment)
                if(fragment!=null){
                    binding.spacer.visibility = View.VISIBLE
                }
            }
        }


        lifecycleScope.launch{
            viewModel.uiState.collect{
                when(it){
                    is SearchingDataState.Loading->{

                    }
                    is SearchingDataState.Success->{
                        resultsAdapter.differ.submitList(it.resultsList)
                    }
                    is SearchingDataState.Error->{

                    }
                }
            }
        }



        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

             override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.updateQueryState(query!!)
                 showResultsInfo()
                binding.searchBar.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.isBlank()){
                    showSearchCache(latestSearchesAdapter.differ.currentList)
                }
                return false
            }
        })


        resultsAdapter.addClickListener {
            viewModel.onEvent(SearchingDataEvents.UpsertSearchCacheInfo(it))
            when(it.type){
                "track"->{
                    val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                    val bundle = Bundle()
                    val trackList = TrackList(listOf(it.toTrackModel()),0)
                    bundle.putParcelable("trackList",trackList)
                    fragmentTransaction?.replace(R.id.mediaFragment,
                        MediaBottomPlayer::class.java,bundle)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                    binding.spacer.visibility = View.VISIBLE
                }
                "album"->{
                    val direction = SearchingDataFragmentDirections.actionSearchingDataFragmentToTracksFragment(it.id,"album",it.title,it.pictureXL)
                    findNavController().navigate(direction)
                }
                "artist"->{
                    val direction = SearchingDataFragmentDirections.actionSearchingDataFragmentToTracksFragment(it.id,"artist",it.title,it.pictureXL)
                    findNavController().navigate(direction)
                }
            }
        }

        resultsAdapter.addClickListener {
            val menu = MusicMenu()
            val bundle = Bundle()
            bundle.putParcelable("track",it.toTrackModel())
            menu.arguments = bundle
            menu.show(parentFragmentManager,"menu")
        }

        latestSearchesAdapter.addClickListener{
            when(it.type){
                "track"->{
                    val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                    val bundle = Bundle()
                    val trackList = TrackList(listOf(it.toTrackModel()),0)
                    bundle.putParcelable("trackList",trackList)
                    fragmentTransaction?.replace(R.id.mediaFragment,
                        MediaBottomPlayer::class.java,bundle)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                    binding.spacer.visibility = View.VISIBLE

                }
                "album"->{
                    val direction = SearchingDataFragmentDirections.actionSearchingDataFragmentToTracksFragment(it.id,"album",it.title,it.pictureXL)
                    findNavController().navigate(direction)
                }
                "artist"->{
                    val direction = SearchingDataFragmentDirections.actionSearchingDataFragmentToTracksFragment(it.id,"artist",it.title,it.pictureXL)
                    findNavController().navigate(direction)
                }
            }
        }

        latestSearchesAdapter.addClickListener {
            viewModel.onEvent(SearchingDataEvents.DeleteSearchCache(it))
        }

        binding.deleteAllSearches.setOnClickListener {
            viewModel.onEvent(SearchingDataEvents.DeleteAllSearchCacheInfo)
        }

        binding.popBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun showSearchCache(list: List<ResultModel>){
        binding.rvSearchResults.visibility = View.GONE
        if(list.isEmpty()){
            binding.noCache.visibility = View.VISIBLE
            binding.searchesText.visibility = View.GONE
            binding.rvLatestSearches.visibility = View.GONE
            binding.deleteAllSearches.visibility = View.GONE
        }
        else{
            binding.noCache.visibility = View.GONE
            binding.searchesText.visibility = View.VISIBLE
            binding.rvLatestSearches.visibility = View.VISIBLE
            binding.deleteAllSearches.visibility = View.VISIBLE
        }
    }
    private fun showResultsInfo(){
        binding.noCache.visibility = View.GONE
        binding.searchesText.visibility = View.GONE
        binding.rvLatestSearches.visibility = View.GONE
        binding.deleteAllSearches.visibility = View.GONE
        binding.rvSearchResults.visibility = View.VISIBLE
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}