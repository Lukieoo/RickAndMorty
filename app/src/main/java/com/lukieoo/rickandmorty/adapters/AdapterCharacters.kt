package com.lukieoo.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukieoo.rickandmorty.databinding.ItemCharacterBinding
import com.lukieoo.rickandmorty.models.characters.Result
import com.lukieoo.rickandmorty.util.AdapterOnClickListener


class AdapterCharacters :
    RecyclerView.Adapter<AdapterCharacters.ViewHolder>() {

    private var character: ArrayList<Result> = arrayListOf()
    private lateinit var binding: ItemCharacterBinding
    private lateinit var adapterOnClickListener: AdapterOnClickListener

    fun setAdapterOnClickListener(adapterOnClickListener: AdapterOnClickListener) {
        this.adapterOnClickListener = adapterOnClickListener
    }

    fun addCharacters(characters: List<Result>) {
        for (character in characters) {
            this.character.add(character)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val characterModel = character[position]
        holder.bind(characterModel)

        holder.itemView.setOnClickListener {
            adapterOnClickListener.onClick(characterModel, holder.binding.characterImage)
        }

    }

    override fun getItemCount(): Int {
        return character.size
    }

    inner class ViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //Init Data Binding
        fun bind(result: Result) {
            binding.characterData = result
            binding.executePendingBindings()
        }
    }
}

