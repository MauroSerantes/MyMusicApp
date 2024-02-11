package com.myapps.mymusic.ui.search.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.ResultItemBinding
import com.myapps.mymusic.databinding.TrackModelItemBinding
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.data.TrackList
import com.myapps.mymusic.ui.player.main.PlayerActivity
import com.squareup.picasso.Picasso
import javax.inject.Inject

class TracksResultAdapter  @Inject constructor(): RecyclerView.Adapter<TracksResultAdapter .ViewHolder>() {

    private lateinit var binding: ResultItemBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ResultItemBinding.inflate(inflater, parent, false)
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
            binding.itemName.text = item.title
            binding.typeResponse.text = context.getString(R.string.track_response)
            binding.buttonFunctionalitiesContainer.visibility = View.VISIBLE
            Picasso.get().load(item.albumCover).into(binding.ivRepresentativeImage)

            val list = listOf(item)
            val trackList = TrackList(list,0)

            binding.playButton.setOnClickListener{
                val intent = Intent(context, PlayerActivity::class.java)
                intent.putExtra("trackList",trackList)
                context.startActivity(intent)
            }
            binding.favouriteButton.setOnClickListener{
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