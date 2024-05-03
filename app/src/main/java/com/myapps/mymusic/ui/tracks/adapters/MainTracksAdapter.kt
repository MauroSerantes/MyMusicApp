package com.myapps.mymusic.ui.tracks.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.TrackModelItemBinding
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.data.TrackList
import com.myapps.mymusic.ui.player.main.PlayerActivity
import com.myapps.mymusic.ui.tracks.MusicMenu
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MainTracksAdapter @Inject constructor() : RecyclerView.Adapter<MainTracksAdapter.ViewHolder>() {

    private lateinit var binding: TrackModelItemBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = TrackModelItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrackModel) {
            binding.songName.text = item.title
            binding.artistName.text = item.artistName
            if(item.albumCover.isBlank()){
                binding.siTrackImage.background = AppCompatResources.getDrawable(context, R.drawable.no_image_available)
            }
            else{
                Picasso.get().load(item.albumCover).into(binding.siTrackImage)
            }

            binding.menuButton.setOnClickListener{
                listOfClickListeners[0](item)
            }

            val list = listOf(item)
            val trackList = TrackList(list,0)

            binding.root.setOnClickListener {
                listOfClickListeners[1](item)
            }

        }
    }

    private val listOfClickListeners = ArrayList<(TrackModel)->Unit>()

    fun addOnItemClickListener(listener: (TrackModel) -> Unit) {
        listOfClickListeners.add(listener)
    }

    private val differCallback = object : DiffUtil.ItemCallback<TrackModel>() {
        override fun areItemsTheSame(oldItem: TrackModel, newItem: TrackModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TrackModel, newItem: TrackModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}