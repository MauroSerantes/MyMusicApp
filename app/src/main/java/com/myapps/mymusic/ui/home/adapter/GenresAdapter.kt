package com.myapps.mymusic.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.data.remote.model.genre.Genre
import com.myapps.mymusic.databinding.HomeItemBinding
import com.myapps.mymusic.domain.GenreModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class GenresAdapter @Inject constructor() : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

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
        fun bind(item: GenreModel) {
            binding.tvItemTextDescription.text = item.name
            Picasso.get().load(item.pictureBig).into(binding.ivItemSourceImage)
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var onItemClickListener: ((GenreModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (GenreModel) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<GenreModel>() {
        override fun areItemsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}