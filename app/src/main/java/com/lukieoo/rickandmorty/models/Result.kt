package com.lukieoo.rickandmorty.models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

data class Result(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
){
     @BindingAdapter("ImageLoad")
        fun setImageUrl(imageView: ImageView, url: String?) {
            Picasso.get().load(url).into(imageView);
        }

}