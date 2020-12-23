package com.lukieoo.rickandmorty

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.lukieoo.rickandmorty.adapters.AdapterCharacters
import com.lukieoo.rickandmorty.databinding.ActivityMainBinding
import com.lukieoo.rickandmorty.viewModel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var adapterCharacters: AdapterCharacters

    private val characterViewModel: CharacterViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        adapterCharacters.setActivityForAnimation(this)

        binding.charactersList.adapter = adapterCharacters

        characterViewModel.observeCharacters().observe(this, Observer {
            adapterCharacters.setCharacter(it.results)
        })

    }
}