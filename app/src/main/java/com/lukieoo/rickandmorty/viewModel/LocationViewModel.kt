package com.lukieoo.rickandmorty.viewModel

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukieoo.rickandmorty.models.episodes.Episode
import com.lukieoo.rickandmorty.models.location.Location
import com.lukieoo.rickandmorty.retrofit.ApiRetrofit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import java.util.regex.Matcher
import java.util.regex.Pattern

class LocationViewModel
@ViewModelInject constructor(private val apiRetrofit: ApiRetrofit) : ViewModel() {
    private var locationLiveData: MutableLiveData<Location> =
        MutableLiveData<Location>()

    fun getLocationSubscribe(url: String) {
        val p: Pattern = Pattern.compile("\\d+")
        val m: Matcher = p.matcher(url)
        while (m.find()) {
            apiRetrofit.getLocation(m.group().toInt()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    locationLiveData.value = it

                }, { t ->
                    t.printStackTrace()
                    locationLiveData.value = null
                })
        }
    }

    fun observeLocation(): MutableLiveData<Location> {
        return locationLiveData
    }
}
