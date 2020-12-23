package com.lukieoo.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lukieoo.rickandmorty.databinding.ActivityMainBinding
import com.lukieoo.rickandmorty.models.ApiDataModel
import com.lukieoo.rickandmorty.retrofit.ApiRetrofit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiRetrofit: ApiRetrofit

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        apiRetrofit.getCharacters(1).enqueue(object : Callback<ApiDataModel> {
            override fun onFailure(call: Call<ApiDataModel>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(call: Call<ApiDataModel>, response: Response<ApiDataModel>) {
                println("LogOn " + response.body().toString())
            }
        })


    }
}