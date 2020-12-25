package com.lukieoo.rickandmorty.models.characters

import androidx.databinding.BaseObservable
import com.lukieoo.rickandmorty.models.characters.Location
import com.lukieoo.rickandmorty.models.characters.Origin

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
): BaseObservable()