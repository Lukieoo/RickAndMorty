package com.lukieoo.rickandmorty.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.lukieoo.rickandmorty.models.characters.ApiDataModel
import com.lukieoo.rickandmorty.models.episodes.Episode
import com.lukieoo.rickandmorty.retrofit.ApiRetrofit
import com.lukieoo.rickandmorty.util.DataWithStates
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.regex.Matcher
import java.util.regex.Pattern

class DetailViewModel
@ViewModelInject constructor(private val apiRetrofit: ApiRetrofit) : ViewModel() {

    private var charactersLiveData: MediatorLiveData<Episode> =
        MediatorLiveData<Episode>()



    fun getEpisodesSubscribe(episodes: List<String>) {
        val p: Pattern = Pattern.compile("\\d+")
//        var counter = 0
//        for (episode in episodes) {
//
//            val m: Matcher = p.matcher(episode)
//            while (m.find()) {
//                apiRetrofit.getEpisode(m.group().toInt()).observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({
//                        listOfEpisode.add(it)
//                        counter++
//                        if (counter == characterDetails.episode.size) {
//                            hideProgress()
//                            adapterEpisodes.addEpisodes(listOfEpisode)
//                        }
//                    }, { t ->
//                        t.printStackTrace()
//                        hideProgress()
//                    })
//            }
//
//        }
        for (episode in episodes) {

            val m: Matcher = p.matcher(episode)
            while (m.find()) {
                val source: LiveData<DataWithStates<Episode>> =
                    LiveDataReactiveStreams.fromPublisher(

                        apiRetrofit.getEpisode(m.group().toInt())
                            .observeOn(AndroidSchedulers.mainThread()).map { characters ->
                                DataWithStates(
                                    characters
                                )
                            }.onErrorReturn { ex ->
                                DataWithStates(
                                    states = ex
                                )
                            }
                            .subscribeOn(Schedulers.io())
                    )

                charactersLiveData.addSource<DataWithStates<Episode>>(
                    source
                ) { t ->
                    charactersLiveData.value = t.data
                    charactersLiveData.removeSource(source)
                }
            }
        }
    }

    fun observeCharacters(): LiveData<Episode> {
        return charactersLiveData
    }
}