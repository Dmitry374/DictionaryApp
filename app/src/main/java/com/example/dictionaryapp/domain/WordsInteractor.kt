package com.example.dictionaryapp.domain

import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.repository.WordsRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WordsInteractor(
    private val wordsRepository: WordsRepository
) {

    fun loadAllWordsByLanguage(language: String): Single<List<Word>> {
        return wordsRepository.loadAllWordsByLanguage(language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchWords(language: String, query: String): Single<List<Word>> {
        return wordsRepository.searchWords(
            language = language,
            query = query
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}