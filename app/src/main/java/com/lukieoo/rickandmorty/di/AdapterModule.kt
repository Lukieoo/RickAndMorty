package com.lukieoo.rickandmorty.di

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.app.ActivityOptionsCompat
import com.lukieoo.rickandmorty.DetailActivity
import com.lukieoo.rickandmorty.adapters.AdapterCharacters
import com.lukieoo.rickandmorty.models.Result
import com.lukieoo.rickandmorty.util.AdapterOnClickListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Singleton
    @Provides
    fun provideAdapterGenres(): AdapterCharacters {
        return AdapterCharacters()
    }
}