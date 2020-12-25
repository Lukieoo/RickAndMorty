package com.lukieoo.rickandmorty.util

import android.widget.ImageView
import com.lukieoo.rickandmorty.models.characters.Result

/**
 * Interface For onClick Event
 */
interface AdapterOnClickListener {
    fun onClick(result: Result, imageView : ImageView)
}