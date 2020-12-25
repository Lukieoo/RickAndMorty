package com.lukieoo.rickandmorty

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.lukieoo.rickandmorty.databinding.ActivityDetailBinding
import com.lukieoo.rickandmorty.models.characters.Result
import com.lukieoo.rickandmorty.retrofit.ApiRetrofit
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var characterDetails: Result

    @Inject
    lateinit var apiRetrofit: ApiRetrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Init view and Data for DataBinding
        initView()
        //Get Api data from Episode
        fetchEpisodeData()
        //Get Api data from Origin Location
        fetchOriginLocation()

    }

    private fun fetchEpisodeData() {
        val listOfEpisode = arrayListOf<String>()

        val p: Pattern = Pattern.compile("\\d+")

        var counter = 0
        for (episode in characterDetails.episode) {

            val m: Matcher = p.matcher(episode)
            while (m.find()) {
                apiRetrofit.getEpisode(m.group().toInt()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        listOfEpisode.add("${it.episode} ${it.name} ${it.air_date} ")
                        counter++
                        if (counter == characterDetails.episode.size) {
                            val adapter =
                                ArrayAdapter(this, R.layout.simple_list_item_1, listOfEpisode)
                            binding.episodeList.adapter = adapter
                        }
                    }
            }

        }
    }

    private fun fetchOriginLocation() {
        val p: Pattern = Pattern.compile("\\d+")
        val m: Matcher = p.matcher(characterDetails.origin.url)
        while (m.find()) {
            apiRetrofit.getLocation(m.group().toInt()).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.characterOriginName.text = it.name
                    binding.characterOriginType.text = it.type
                    binding.characterOriginDimension.text = it.dimension

                }
        }
    }

    private fun initView() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        getCharacterDetails()
        val view = binding.root
        setContentView(view)
        supportPostponeEnterTransition()

        binding.returnButton.setOnClickListener {
            finishAfterTransition()

        }

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

    private fun getCharacterDetails() {
        val gson = Gson()
        val strObj: String? = intent.getStringExtra("characterDetails")
        characterDetails = gson.fromJson(strObj, Result::class.java)
        binding.characterDetail = characterDetails
    }
}