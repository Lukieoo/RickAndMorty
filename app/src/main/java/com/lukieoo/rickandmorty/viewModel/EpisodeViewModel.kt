package com.lukieoo.rickandmorty.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.lukieoo.rickandmorty.models.episodes.Episode
import com.lukieoo.rickandmorty.retrofit.ApiRetrofit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.regex.Matcher
import java.util.regex.Pattern

class EpisodeViewModel
@ViewModelInject constructor(private val apiRetrofit: ApiRetrofit) : ViewModel() {

    private var episodesLiveData: MutableLiveData<List<Episode>> =
        MutableLiveData<List<Episode>>()

    fun getEpisodesSubscribe(episodes: List<String>) {

        val listOfEpisode = arrayListOf<Episode>()

        val p: Pattern = Pattern.compile("\\d+")
        var counter = 0
        for (episode in episodes) {

            val m: Matcher = p.matcher(episode)
            while (m.find()) {
                apiRetrofit.getEpisode(m.group().toInt()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        listOfEpisode.add(it)
                        counter++
                        if (counter == episodes.size) {
                            episodesLiveData.value = listOfEpisode
                        }
                    }, { t ->
                        t.printStackTrace()
                    })
            }

        }
    }

    fun observeEpisodes(): MutableLiveData<List<Episode>> {
        return episodesLiveData
    }
}