package com.example.dictionaryapp.di

import com.example.dictionaryapp.ui.words.WordsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, ViewModelBuilderModule::class])
interface AppComponent {

    fun inject(moviesFragment: WordsFragment)
}