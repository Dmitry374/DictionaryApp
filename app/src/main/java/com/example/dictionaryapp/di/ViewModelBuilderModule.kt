package com.example.dictionaryapp.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilderModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: WordsViewModelFactory
    ): ViewModelProvider.Factory
}