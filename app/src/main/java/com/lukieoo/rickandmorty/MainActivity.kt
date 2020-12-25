package com.lukieoo.rickandmorty

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.lukieoo.rickandmorty.adapters.AdapterCharacters
import com.lukieoo.rickandmorty.databinding.ActivityMainBinding
import com.lukieoo.rickandmorty.models.characters.ApiDataModel
import com.lukieoo.rickandmorty.models.characters.Result
import com.lukieoo.rickandmorty.util.AdapterOnClickListener
import com.lukieoo.rickandmorty.viewModel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var adapterCharacters: AdapterCharacters
    private val characterViewModel: CharacterViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var isEnd = false
    private var page = 1
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView with viewBinding
        initViewBinding()
        // init Adapter for recyclerView
        initRecyclerView()
        // set listener for pagination per 20
        setRecyclerViewPagination()
        // init view model observer for data
        initViewModel()

    }

    private fun setRecyclerViewPagination() {
        isEnd = false
        binding.charactersList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    if (!isEnd) {
                        isEnd = true
                        if (adapterCharacters.itemCount > 0) {
                            //  showProgressBar()
                            loadMoreCharacters()

                        }
                    }

                }
            }
        })
    }

    private fun loadMoreCharacters() {

        page++
        characterViewModel.getCharacterByPage(page)
    }

    private fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initViewModel() {
        characterViewModel.observeCharacters().observe(this, Observer {
            adapterCharacters.addCharacters(it.results)
            checkIsLastPage(it)
            updateView(it)
        })
    }

    private fun updateView(it: ApiDataModel) {
        binding.numberCharacters.text = "${adapterCharacters.itemCount}/${it.info.count}"
    }

    private fun checkIsLastPage(it: ApiDataModel) {
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                isEnd = page == it.info.pages
            }
        }, 600)
    }

    private fun initRecyclerView() {
        adapterCharacters.setAdapterOnClickListener(object : AdapterOnClickListener {
            override fun onClick(result: Result, imageView: ImageView) {
                val intent = Intent(application.applicationContext, DetailActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val gson: Gson = Gson()
                intent.putExtra("characterDetails", gson.toJson(result))

                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
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