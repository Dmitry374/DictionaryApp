package com.example.dictionaryapp.ui.words

import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.domain.WordsInteractor
import javax.inject.Inject

class WordsViewModel @Inject constructor(
    private val wordsInteractor: WordsInteractor
) : ViewModel() {
}