package com.lukieoo.rickandmorty.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.lukieoo.rickandmorty.models.characters.ApiDataModel
import com.lukieoo.rickandmorty.retrofit.ApiRetrofit
import com.lukieoo.rickandmorty.util.DataWithStates
import io.reactivex.rxjava3.schedulers.Schedulers

class CharacterViewModel
@ViewModelInject constructor(private val apiRetrofit: ApiRetrofit) : ViewModel() {

    private val charactersLiveData: MediatorLiveData<ApiDataModel> =
        MediatorLiveData<ApiDataModel>()

    init {
        getCharacterByPage(1)
    }

    fun getCharacterByPage(page: Int) {
        val source: LiveData<DataWithStates<ApiDataModel>> = LiveDataReactiveStreams.fromPublisher(
            apiRetrofit.getCharacters(page).observeOn(Schedulers.io()).map { characters ->
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

        charactersLiveData.addSource(
            source
        ) { t ->
            charactersLiveData.value = t.data
            charactersLiveData.removeSource(source)
        }

    }

    fun observeCharacters(): LiveData<ApiDataModel> {
        return charactersLiveData
    }
}