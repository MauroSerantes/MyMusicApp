package com.myapps.mymusic.ui.artist

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
import com.myapps.mymusic.ui.artist.adapters.MainArtistsAdapter
import com.myapps.mymusic.databinding.FragmentArtistsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ArtistsFragment : Fragment() {

    private var _binding: FragmentArtistsBinding?=null
    private val binding get() = _binding!!

    private val viewModel:ArtistsViewModel by viewModels()
    private val args:ArtistsFragmentArgs by navArgs()


    @Inject
    lateinit var artistsAdapter:MainArtistsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // load background
        binding.rvArtists.background = ContextCompat.getDrawable(requireContext(),R.drawable.background_tracks)

        //config Adapter
        binding.rvArtists.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.rvArtists.adapter = artistsAdapter

        artistsAdapter.setOnItemClickListener {
            val direction = ArtistsFragmentDirections.actionArtistsFragmentToTracksFragment(it.id,"artist",null,null)
            findNavController().navigate(direction)
        }


        //prepare to show data
        lifecycleScope.launch {
            viewModel.getArtistByMusicalGenre(args.genreId)

            viewModel.uiState.collect{
                when(it){
                    is ArtistsUiState.Loading->{

                    }
                    is ArtistsUiState.Success->{
                        artistsAdapter.differ.submitList(it.artists)
                    }
                    is ArtistsUiState.Error->{

                    }
                }
            }
        }


        //config back Button
        binding.backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}