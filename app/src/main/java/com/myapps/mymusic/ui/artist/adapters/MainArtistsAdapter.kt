package com.myapps.mymusic.ui.artist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.databinding.ArtistModelItemBinding
import com.myapps.mymusic.domain.ArtistModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MainArtistsAdapter @Inject constructor() : RecyclerView.Adapter<MainArtistsAdapter.ViewHolder>() {

    private lateinit var binding: ArtistModelItemBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ArtistModelItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArtistModel) {
            binding.tvArtistName.text = item.name
            Picasso.get().load(item.pictureXL).into(binding.sivArtistImage)
            binding.root.setOnClickListener{
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var onItemClickListener: ((ArtistModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (ArtistModel) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<ArtistModel>() {
        override fun areItemsTheSame(oldItem: ArtistModel, newItem: ArtistModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArtistModel, newItem: ArtistModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}