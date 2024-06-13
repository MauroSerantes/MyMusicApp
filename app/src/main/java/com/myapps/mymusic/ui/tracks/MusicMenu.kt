package com.myapps.mymusic.ui.tracks

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.BottomMusicMenuBinding
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.utils.getParcelableExtraCompat
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicMenu: BottomSheetDialogFragment() {

    private lateinit var binding : BottomMusicMenuBinding
    private val viewModel by viewModels<TracksViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomMusicMenuBinding.inflate(inflater, container, false)
        val trackModel = arguments?.getParcelableExtraCompat("track",TrackModel::class.java)

        binding.artistName.text = trackModel?.artistName
        binding.songName.text = trackModel?.title
        if(trackModel?.albumCover?.isBlank() == true){
            binding.siTrackImage.background = context?.let { AppCompatResources.getDrawable(it, R.drawable.no_image_available) }
        }
        else{
            Picasso.get().load(trackModel?.albumCover).into(binding.siTrackImage)
        }

        binding.addToFavourite.setOnClickListener {
            viewModel.onEvent(TracksEvents.UpsertFavTrack(trackModel!!))
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

}