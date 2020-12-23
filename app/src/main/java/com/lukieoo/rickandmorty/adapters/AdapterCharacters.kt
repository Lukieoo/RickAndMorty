package com.lukieoo.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukieoo.rickandmorty.databinding.ItemCharacterBinding
import com.lukieoo.rickandmorty.models.Result
import com.lukieoo.rickandmorty.util.AdapterOnClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*

class AdapterCharacters(var adapterOnClickListener: AdapterOnClickListener) :
    RecyclerView.Adapter<AdapterCharacters.ViewHolder>() {

    lateinit var character: ArrayList<Result>
    private lateinit var binding: ItemCharacterBinding

    override fun getItemCount(): Int {
        return if (::character.isInitialized) {

            character.size
        } else {
            0
        }

    }

    fun setCharacter(character: List<Result>) {
        this.character = character as ArrayList<Result>
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)

    }

    // Binds each item in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val characterModel = character[position]
        holder.characterName.text = characterModel.name
        holder.characterOrigin.text = characterModel.origin.name
        Picasso.get()
            .load(characterModel.image)
            .into(holder.characterImage)

        holder.itemView.setOnClickListener {
            adapterOnClickListener.onClick(characterModel)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each picture to
        val characterImage: ImageView = view.character_image
        val characterName: TextView = view.character_name
        val characterOrigin: TextView = view.character_origin

    }
}

