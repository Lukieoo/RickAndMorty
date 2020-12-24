package com.lukieoo.rickandmorty.util

import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import com.lukieoo.rickandmorty.models.Result

/**
 * Interface For onClick Event
 */
interface AdapterOnClickListener {
    fun onClick(result: Result,imageView : ImageView)
}