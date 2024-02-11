package com.myapps.mymusic.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.databinding.HomeItemBinding
import com.myapps.mymusic.domain.AlbumModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class TopAlbumsAdapter @Inject constructor() : RecyclerView.Adapter<TopAlbumsAdapter.ViewHolder>() {

    private lateinit var binding: HomeItemBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = HomeItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlbumModel) {
            binding.tvItemTextDescription.text = item.title
            Picasso.get().load(item.coverXL).into(binding.ivItemSourceImage)
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