package com.myapps.mymusic.ui.search.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapps.mymusic.core.mappers.toTrackModel
import com.myapps.mymusic.databinding.ResultItemBinding
import com.myapps.mymusic.ui.search.model.ResultModel
import com.myapps.mymusic.ui.tracks.MusicMenu
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ResultAdapter @Inject constructor():  RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    private lateinit var binding: ResultItemBinding
    private lateinit var context: Context
    private val clickListeners = ArrayList<((ResultModel)->Unit)>()

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
        fun bind(item: ResultModel) {
            binding.itemName.text = item.title
            binding.typeResponse.text = item.type
            if(item.type == "track"){
                binding.menuButton.visibility = View.VISIBLE
            }
            Picasso.get().load(item.pictureXL).into(binding.ivRepresentativeImage)

            binding.root.setOnClickListener {
                clickListeners[0](item)
            }

            binding.menuButton.setOnClickListener{
                clickListeners[1](item)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ResultModel>() {
        override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
            return oldItem == newItem
        }
    }

    fun addClickListener(listener: (ResultModel) -> Unit){
        clickListeners.add(listener)
    }


    val differ = AsyncListDiffer(this, differCallback)
}