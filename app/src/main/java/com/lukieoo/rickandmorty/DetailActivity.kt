package com.lukieoo.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.Window
import com.lukieoo.rickandmorty.databinding.ActivityDetailBinding
import com.lukieoo.rickandmorty.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_character.view.*


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Picasso.get().load(intent.getStringExtra("image")).noFade().into(binding.characterImage)

    }
}