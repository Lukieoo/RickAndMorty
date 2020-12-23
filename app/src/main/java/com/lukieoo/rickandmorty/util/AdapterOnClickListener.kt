package com.lukieoo.rickandmorty.util

import androidx.core.app.ActivityOptionsCompat
import com.lukieoo.rickandmorty.models.Result

/**
 * Interface For onClick Event
 */
interface AdapterOnClickListener {
    fun onClick(result: Result, bundle: ActivityOptionsCompat?)
}