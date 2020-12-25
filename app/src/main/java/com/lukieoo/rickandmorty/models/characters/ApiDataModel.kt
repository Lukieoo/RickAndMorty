package com.lukieoo.rickandmorty.models.characters

data class ApiDataModel(
    val info: Info,
    val results: List<Result>
)