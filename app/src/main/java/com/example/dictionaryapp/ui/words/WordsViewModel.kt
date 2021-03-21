package com.example.dictionaryapp.ui.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.domain.WordsInteractor
import com.example.dictionaryapp.model.Word
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WordsViewModel @Inject constructor(
    private val wordsInteractor: WordsInteractor
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _words by lazy { MutableLiveData<List<Word>>() }
    val words: LiveData<List<Word>>
        get() = _words

    fun loadAllWordsByLanguage(language: String) {
        compositeDisposable.add(
            wordsInteractor.loadAllWordsByLanguage(language)
                .subscribe({ words ->
                    _words.value = words
                }, {

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}