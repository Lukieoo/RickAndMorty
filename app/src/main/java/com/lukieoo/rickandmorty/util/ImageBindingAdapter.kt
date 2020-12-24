package com.lukieoo.rickandmorty.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

/**
 * BindingAdapter for ImageView
 */

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:image")
    fun imageUrl(view: ImageView, url: String) {
        Picasso.get().load(url).into(view)

    }
}