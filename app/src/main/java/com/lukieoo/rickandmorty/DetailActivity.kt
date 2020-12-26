package com.lukieoo.rickandmorty

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.lukieoo.rickandmorty.adapters.AdapterEpisodes
import com.lukieoo.rickandmorty.databinding.ActivityDetailBinding
import com.lukieoo.rickandmorty.models.characters.Result
import com.lukieoo.rickandmorty.util.ProgressStatus
import com.lukieoo.rickandmorty.viewModel.EpisodeViewModel
import com.lukieoo.rickandmorty.viewModel.LocationViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), ProgressStatus {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var characterDetails: Result

    @Inject
    lateinit var adapterEpisodes: AdapterEpisodes

    private val episodeViewModel: EpisodeViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //Init view and Data for DataBinding
        initView()
        // set Toolbar event navigation
        initToolbar()
        //Get Api data from Episode
        initEpisodeViewModel()
        // init Adapter for recyclerView
        initRecyclerView()
        //Get Api data from Origin Location
        initLocationViewModel()

    }

    private fun initRecyclerView() {
        binding.episodeList.adapter = adapterEpisodes
    }

    private fun initEpisodeViewModel() {

        if (adapterEpisodes.itemCount == 0) {
            showProgress()
        }

        episodeViewModel.getEpisodesSubscribe(characterDetails.episode)
        episodeViewModel.observeEpisodes().observe(this, {
            if (it != null) {
                adapterEpisodes.setEpisodes(it)
            }
            hideProgress()
        })

    }

    private fun initLocationViewModel() {
        locationViewModel.getLocationSubscribe(characterDetails.origin.url)
        locationViewModel.observeLocation().observe(this, {
            if (it != null) {
                if (it.name.trim() != "") {
                    binding.layoutOriginName.visibility = View.VISIBLE
                    binding.characterOriginName.text = it.name
                }
                if (it.type.trim() != "") {
                    binding.layoutOriginType.visibility = View.VISIBLE
                    binding.characterOriginType.text = it.type
                }
                if (it.dimension.trim() != "") {
                    binding.layoutOriginDimension.visibility = View.VISIBLE
                    binding.characterOriginDimension.text = it.dimension
                }
            }
            hideProgress()
        })
    }

    private fun initView() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        getCharacterDetails()
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
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.navigationIcon =
            resources.getDrawable(R.drawable.ic_baseline_keyboard, null)
        binding.toolbar.setNavigationOnClickListener { finishAfterTransition() }
    }

    private fun getCharacterDetails() {
        val gson = Gson()
        val strObj: String? = intent.getStringExtra("characterDetails")
        characterDetails = gson.fromJson(strObj, Result::class.java)
        binding.characterDetail = characterDetails
    }

    override fun hideProgress() {
        binding.refreshLayout.visibility = View.GONE
    }

    override fun showProgress() {
        binding.refreshLayout.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        val isChangingConfigurations = this.isChangingConfigurations
        if (!isChangingConfigurations) {
            adapterEpisodes.clearEpisodes()
        }

        super.onDestroy()
    }
}