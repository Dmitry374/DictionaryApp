package com.example.dictionaryapp.ui.translate

import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.domain.WordsInteractor
import javax.inject.Inject

class TranslateViewModel @Inject constructor(
    private val wordsInteractor: WordsInteractor
) : ViewModel() {

}