package com.example.dictionaryapp.di

import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.ui.translate.TranslateViewModel
import com.example.dictionaryapp.ui.words.WordsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WordsViewModel::class)
    abstract fun bindWordsViewModel(wordsViewModel: WordsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TranslateViewModel::class)
    abstract fun bindTranslateViewModel(translateViewModel: TranslateViewModel): ViewModel
}