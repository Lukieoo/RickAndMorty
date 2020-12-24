package com.lukieoo.rickandmorty

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.lukieoo.rickandmorty.adapters.AdapterCharacters
import com.lukieoo.rickandmorty.databinding.ActivityMainBinding
import com.lukieoo.rickandmorty.models.Result
import com.lukieoo.rickandmorty.util.AdapterOnClickListener
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

        // setContentView with viewBinding
        initViewBinding()
        // init Adapter for recyclerView
        initRecyclerView()
        // init view model observer for data
        initViewModel()

    }

    private fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initViewModel() {
        characterViewModel.observeCharacters().observe(this, Observer {
            adapterCharacters.setCharacter(it.results)
        })
    }

    private fun initRecyclerView() {
        adapterCharacters.setAdapterOnClickListener(object : AdapterOnClickListener {
            override fun onClick(result: Result, imageView: ImageView) {
                val intent = Intent(application.applicationContext, DetailActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val gson: Gson = Gson()
                intent.putExtra("characterDetails", gson.toJson(result))

                var bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MainActivity,
                    imageView,
                    "avatar"
                )
                startActivity(intent, bundle.toBundle())

            }
        })
        binding.charactersList.adapter = adapterCharacters
    }
}