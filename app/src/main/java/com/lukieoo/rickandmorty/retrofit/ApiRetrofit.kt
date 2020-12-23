package com.lukieoo.rickandmorty.retrofit

import com.lukieoo.rickandmorty.models.ApiDataModel
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Query from https://rickandmortyapi.com/documentation
 */
interface ApiRetrofit {
    @GET("character/")
    fun getCharacters(@Query("page") page: Int): Flowable<ApiDataModel>
}