package com.myapps.mymusic.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.R
import com.myapps.mymusic.data.remote.model.tracks.Track
import com.myapps.mymusic.databinding.HomeItemTrackBinding
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.data.TrackList
import com.myapps.mymusic.ui.player.main.PlayerActivity
import com.squareup.picasso.Picasso
import javax.inject.Inject

class TopTracksAdapter @Inject constructor(): RecyclerView.Adapter<TopTracksAdapter.ViewHolder>() {

    private lateinit var binding: HomeItemTrackBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = HomeItemTrackBinding.inflate(inflater, parent, false)
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
            Picasso.get().load(item.albumCover).into(binding.ivAlbumCoverSong)

            val list = listOf(item)
            val trackList = TrackList(list,0)

            binding.ivPlayButton.setOnClickListener{
                val intent = Intent(context,PlayerActivity::class.java)
                intent.putExtra("trackList",trackList)
                context.startActivity(intent)
            }

            binding.ivFavouriteButton.setOnClickListener{
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var onItemClickListener: ((TrackModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (TrackModel) -> Unit) {
        onItemClickListener = listener
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