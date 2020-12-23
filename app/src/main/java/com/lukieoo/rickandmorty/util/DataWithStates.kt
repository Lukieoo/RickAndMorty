package com.lukieoo.rickandmorty.util

/**
 * Throwable any error of Api Data
 * @param <T> Model of Data
 */
data class DataWithStates<T>(
    val data: T? = null,
    val states: Throwable? = null
)
