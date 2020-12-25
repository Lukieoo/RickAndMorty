package com.lukieoo.rickandmorty.di

import com.lukieoo.rickandmorty.adapters.AdapterCharacters
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