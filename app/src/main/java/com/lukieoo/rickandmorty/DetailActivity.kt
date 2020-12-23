package com.lukieoo.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lukieoo.rickandmorty.databinding.ActivityDetailBinding
import com.lukieoo.rickandmorty.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}