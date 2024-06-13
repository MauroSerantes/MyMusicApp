package com.myapps.mymusic.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.RadioItemBinding
import com.myapps.mymusic.domain.RadioModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class RadiosAdapter @Inject constructor() : RecyclerView.Adapter<RadiosAdapter.ViewHolder>() {

    private lateinit var binding: RadioItemBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding =  RadioItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:  RadioModel) {
            binding.tvRadioName.text= item.title

            if(item.pictureXL.isBlank()){
                binding.ivRadio.background = AppCompatResources.getDrawable(context, R.drawable.no_image_available)
            }
            else Picasso.get().load(item.pictureXL).into(binding.ivRadio)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var onItemClickListener: ((RadioModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (RadioModel) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<RadioModel>() {
        override fun areItemsTheSame(oldItem: RadioModel, newItem:  RadioModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem:  RadioModel, newItem:  RadioModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}