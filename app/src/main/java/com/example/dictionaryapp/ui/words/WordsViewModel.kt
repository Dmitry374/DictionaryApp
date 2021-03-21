package com.example.dictionaryapp.ui.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.common.Constants
import com.example.dictionaryapp.domain.WordsInteractor
import com.example.dictionaryapp.model.Word
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WordsViewModel @Inject constructor(
    private val wordsInteractor: WordsInteractor
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val searchSubject = PublishSubject.create<String>()

    var wordsLanguage = Constants.LANGUAGE_EN

    private val _words by lazy { MutableLiveData<List<Word>>() }
    val words: LiveData<List<Word>>
        get() = _words

    fun searsNewWords(newText: String) {
        searchSubject.onNext(newText)
    }

    fun searchWords() {

        val searchMovieDisposable = searchSubject
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMapSingle { query ->
                wordsInteractor.searchWords(
                    language = wordsLanguage,
                    query = query
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ words ->
                _words.value = words
            }, {

            })

        compositeDisposable.add(searchMovieDisposable)
    }

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