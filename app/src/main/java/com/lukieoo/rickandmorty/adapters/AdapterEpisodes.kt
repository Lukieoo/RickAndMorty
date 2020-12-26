package com.lukieoo.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukieoo.rickandmorty.databinding.ItemEpisodeBinding
import com.lukieoo.rickandmorty.models.episodes.Episode
import com.lukieoo.rickandmorty.util.AdapterOnClickListener


class AdapterEpisodes :
    RecyclerView.Adapter<AdapterEpisodes.ViewHolder>() {

    private var episodes: ArrayList<Episode> = arrayListOf()
    private lateinit var binding: ItemEpisodeBinding
    private lateinit var adapterOnClickListener: AdapterOnClickListener

    fun setAdapterOnClickListener(adapterOnClickListener: AdapterOnClickListener) {
        this.adapterOnClickListener = adapterOnClickListener
    }

    fun clearEpisodes() {
        episodes.clear()
        notifyDataSetChanged()
    }

    fun addEpisodes(episode:  Episode) {
        //for (episode in episodes) {
            this.episodes.add(episode)
        //}
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

    inner class ViewHolder(val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //Init Data Binding
        fun bind(episode: Episode) {
            binding.episodeData = episode
            binding.executePendingBindings()
        }
    }
}

