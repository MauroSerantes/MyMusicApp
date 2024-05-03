package com.myapps.mymusic.ui.mymusic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.FragmentMyMusicBinding
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.mymusic.adapter.MyTracksAdapter
import com.myapps.mymusic.ui.player.MediaBottomPlayer
import com.myapps.mymusic.ui.player.data.TrackList
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

        binding.rvMyTracks.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.rvMyTracks.adapter = favTracksAdapter


        lifecycleScope.launch {
            viewModel.uiState.collect{
                when(it){
                    is MyMusicUiState.Loading->{
                        binding.scrollView.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MyMusicUiState.Success->{
                        binding.scrollView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        favTracksAdapter.differ.submitList(it.tracks)
                        configReproduceAllSongs(it.tracks)
                        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.mediaFragment)
                        if(fragment!=null){
                            binding.spacer.visibility = View.VISIBLE
                        }
                    }
                    is MyMusicUiState.Error->{
                    }
                }
            }
        }

        favTracksAdapter.addOnItemClickListener {
            val bundle = Bundle()
            val trackList = TrackList(listOf(it),0)
            bundle.putParcelable("trackList",trackList)
            binding.spacer.visibility = View.VISIBLE
            activity?.supportFragmentManager?.commit {
                add(R.id.mediaFragment, MediaBottomPlayer::class.java,bundle)
            }
        }

        val itemTouchHelper = object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val track = favTracksAdapter.differ.currentList[viewHolder.adapterPosition]
                viewModel.deleteSong(track)
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvMyTracks)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun configReproduceAllSongs(listOfSongs:List<TrackModel>){
        if(listOfSongs.isEmpty()) return
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
}