package com.lukieoo.rickandmorty.retrofit

import com.lukieoo.rickandmorty.models.ApiDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRetrofit {
    @GET("character/")
    fun getCharacters(@Query("page") page: Int): Call<ApiDataModel>
}