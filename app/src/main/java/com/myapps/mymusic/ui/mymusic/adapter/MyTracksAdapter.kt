package com.myapps.mymusic.ui.mymusic.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.databinding.FavouriteTrackItemBinding
import com.myapps.mymusic.databinding.TrackModelItemBinding
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.data.TrackList
import com.myapps.mymusic.ui.player.main.PlayerActivity
import javax.inject.Inject

class MyTracksAdapter @Inject constructor(): RecyclerView.Adapter<MyTracksAdapter .ViewHolder>() {

    private lateinit var binding: FavouriteTrackItemBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = FavouriteTrackItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrackModel) {
            binding.songName.text = item.title
            binding.songArtistName.text = item.artistName
            binding.ivPlayButton.setOnClickListener{
                val trackList = TrackList(differ.currentList,differ.currentList.indexOf(item))
                val intent = Intent(context, PlayerActivity::class.java)
                intent.putExtra("trackList",trackList)
                context.startActivity(intent)
            }
            binding.ivDeleteButton.setOnClickListener{
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