package com.lukieoo.rickandmorty

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.lukieoo.rickandmorty.adapters.AdapterEpisodes
import com.lukieoo.rickandmorty.databinding.ActivityDetailBinding
import com.lukieoo.rickandmorty.models.characters.Result
import com.lukieoo.rickandmorty.models.episodes.Episode
import com.lukieoo.rickandmorty.retrofit.ApiRetrofit
import com.lukieoo.rickandmorty.util.ProgressStatus
import com.lukieoo.rickandmorty.viewModel.CharacterViewModel
import com.lukieoo.rickandmorty.viewModel.DetailViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), ProgressStatus {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var characterDetails: Result

    @Inject
    lateinit var apiRetrofit: ApiRetrofit

    @Inject
    lateinit var adapterEpisodes: AdapterEpisodes

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //Init view and Data for DataBinding
        initView()
        // set Toolbar event navigation
        initToolbar()
        //Get Api data from Episode
        fetchEpisodeData()
        // init Adapter for recyclerView
        initRecyclerView()
        //Get Api data from Origin Location
        fetchOriginLocation()

    }

    private fun initRecyclerView() {
        adapterEpisodes.clearEpisodes()
        binding.episodeList.adapter = adapterEpisodes
    }

    private fun fetchEpisodeData() {

        showProgress()
        adapterEpisodes.clearEpisodes()
        detailViewModel.getEpisodesSubscribe(characterDetails.episode)
        detailViewModel.observeCharacters().observe(this, {
            if (it != null) {
                adapterEpisodes.addEpisodes(it)
            }
            hideProgress()
        })

    }

    private fun fetchOriginLocation() {
        val p: Pattern = Pattern.compile("\\d+")
        val m: Matcher = p.matcher(characterDetails.origin.url)
        while (m.find()) {
            apiRetrofit.getLocation(m.group().toInt()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
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

                }, { t ->
                    t.printStackTrace()
                    hideProgress()
                })
        }
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
}