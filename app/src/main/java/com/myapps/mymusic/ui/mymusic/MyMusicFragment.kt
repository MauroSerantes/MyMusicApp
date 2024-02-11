package com.myapps.mymusic.ui.mymusic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.mymusic.databinding.FragmentMyMusicBinding
import com.myapps.mymusic.ui.mymusic.adapter.MyTracksAdapter
import com.myapps.mymusic.ui.tracks.TracksUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MyMusicFragment : Fragment() {

    private var _binding:FragmentMyMusicBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MyMusicViewModel>()


    @Inject
    lateinit var favTracksAdapter:MyTracksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyMusicBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favTracksAdapter = MyTracksAdapter()
        binding.rvMyTracks.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvMyTracks.adapter = favTracksAdapter


        lifecycleScope.launch {
            viewModel.uiState.collect{
                when(it){
                    is MyMusicUiState.Loading->{

                    }
                    is MyMusicUiState.Success->{
                        favTracksAdapter.differ.submitList(it.tracks)
                    }
                    is MyMusicUiState.Error->{
                    }
                }
            }
        }

        favTracksAdapter.setOnItemClickListener {
            viewModel.deleteSong(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}