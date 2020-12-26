package com.lukieoo.rickandmorty.di

import com.lukieoo.rickandmorty.adapters.AdapterCharacters
import com.lukieoo.rickandmorty.adapters.AdapterEpisodes
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
    fun provideAdapterCharacters(): AdapterCharacters {
        return AdapterCharacters()
    }
    @Singleton
    @Provides
    fun provideAdapterEpisode(): AdapterEpisodes {
        return AdapterEpisodes()
    }
}