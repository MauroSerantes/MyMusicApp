package com.myapps.mymusic.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.FragmentSearchBinding
import com.myapps.mymusic.ui.search.adapter.GenresAdapter
import com.myapps.mymusic.ui.search.adapter.RadiosAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding:FragmentSearchBinding ?= null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchFragmentViewModel>()


    @Inject
    lateinit var genresAdapter:GenresAdapter
    @Inject
    lateinit var radiosAdapter: RadiosAdapter

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

        binding.searchButton.setOnClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToSearchingDataFragment()
            findNavController().navigate(direction)
        }


        val layout = object : GridLayoutManager(context,2){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }.apply {
            this.orientation = GridLayoutManager.VERTICAL
        }

        binding.rvGenres.layoutManager = layout
        binding.rvGenres.adapter = genresAdapter


        binding.rvRadios.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvRadios.adapter = radiosAdapter


        genresAdapter.setOnItemClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToArtistsFragment(it.id,it.name)
            findNavController().navigate(direction)
        }

        radiosAdapter.setOnItemClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToTracksFragment(it.id,"radio",it.title,it.pictureXL)
            findNavController().navigate(direction)
        }

        lifecycleScope.launch{
            viewModel.state.collect{state->
                when(state){
                    is SearchUiState.Loading->{
                        binding.progressBar.visibility = View.VISIBLE
                        binding.scroll.visibility = View.GONE
                    }
                    is SearchUiState.Success->{
                        binding.progressBar.visibility = View.GONE
                        binding.scroll.visibility = View.VISIBLE
                        genresAdapter.differ.submitList(state.genres)
                        radiosAdapter.differ.submitList(state.topRadios)
                        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.mediaFragment)
                        if(fragment!=null){
                            binding.spacer.visibility = View.VISIBLE
                        }
                    }
                    is SearchUiState.Error->{

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}