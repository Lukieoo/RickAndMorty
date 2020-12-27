package com.lukieoo.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukieoo.rickandmorty.databinding.ItemEpisodeBinding
import com.lukieoo.rickandmorty.models.episodes.Episode


class AdapterEpisodes :
    RecyclerView.Adapter<AdapterEpisodes.ViewHolder>() {

    private var episodes: ArrayList<Episode> = arrayListOf()
    private lateinit var binding: ItemEpisodeBinding

    fun clearEpisodes() {
        episodes.clear()
        notifyDataSetChanged()
    }

    fun setEpisodes(episodes: List<Episode>) {
        this.episodes = episodes as ArrayList<Episode>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(episodes[position])

    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    inner class ViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //Init Data Binding
        fun bind(episode: Episode) {
            binding.episodeData = episode
            binding.executePendingBindings()
        }
    }
}

