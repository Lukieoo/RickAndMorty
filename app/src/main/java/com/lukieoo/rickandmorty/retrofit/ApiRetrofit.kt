package com.lukieoo.rickandmorty.retrofit

import com.lukieoo.rickandmorty.models.characters.ApiDataModel
import com.lukieoo.rickandmorty.models.location.Location
import com.lukieoo.rickandmorty.models.episodes.Episode
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Query from https://rickandmortyapi.com/documentation
 */
interface ApiRetrofit {
    @GET("character/")
    fun getCharacters(@Query("page") page: Int): Flowable<ApiDataModel>

    @GET("episode/{id}")
    fun getEpisode(@Path("id") id: Int): Flowable<Episode>

    @GET("location/{id}")
    fun getLocation(@Path("id") id: Int): Flowable<Location>
}