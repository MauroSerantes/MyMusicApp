package com.myapps.mymusic.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.ResultItemBinding
import com.myapps.mymusic.databinding.TrackModelItemBinding
import com.myapps.mymusic.domain.AlbumModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class AlbumsResultAdapter @Inject constructor(): RecyclerView.Adapter<AlbumsResultAdapter.ViewHolder>() {

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
        fun bind(item: AlbumModel) {
            binding.itemName.text = item.title
            binding.typeResponse.text = context.getString(R.string.album_response)
            Picasso.get().load(item.coverXL).into(binding.ivRepresentativeImage)
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var onItemClickListener: ((AlbumModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (AlbumModel) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<AlbumModel>() {
        override fun areItemsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}