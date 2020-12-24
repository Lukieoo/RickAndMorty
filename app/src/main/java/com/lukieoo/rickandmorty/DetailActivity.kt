package com.lukieoo.rickandmorty

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.lukieoo.rickandmorty.databinding.ActivityDetailBinding
import com.lukieoo.rickandmorty.models.Result
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var characterDetails: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val gson = Gson()
        val strObj: String? = intent.getStringExtra("characterDetails")
        characterDetails = gson.fromJson(strObj, Result::class.java)
        binding.characterDetail = characterDetails
        val view = binding.root

        setContentView(view)

        supportPostponeEnterTransition()

        Picasso.get().load(characterDetails.image).noFade()
            .into(binding.characterImage, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }
            })

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, characterDetails.episode)
        binding.episodeList.adapter = adapter

    }
}