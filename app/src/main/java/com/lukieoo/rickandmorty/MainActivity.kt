package com.lukieoo.rickandmorty

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lukieoo.rickandmorty.adapters.AdapterCharacters
import com.lukieoo.rickandmorty.databinding.ActivityMainBinding
import com.lukieoo.rickandmorty.retrofit.ApiRetrofit
import com.lukieoo.rickandmorty.viewModel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    @Inject
//    lateinit var apiRetrofit: ApiRetrofit


    @Inject
    lateinit var adapterCharacters: AdapterCharacters

    val characterViewModel: CharacterViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

//    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
//    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.charactersList.adapter = adapterCharacters

        characterViewModel.observeCharacters().observe(this, Observer {
            adapterCharacters.setCharacter(it.results)
        })

    }
}