package com.example.dictionaryapp.domain

import com.example.dictionaryapp.common.Constants
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.network.request.NewItem
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

    fun addNewWord(newWord: String, newTranslate: String, language: String): Single<Word> {
        var wordId = -1
        val translateLanguage =
            if (language == Constants.LANGUAGE_EN) Constants.LANGUAGE_RU else Constants.LANGUAGE_EN
        return wordsRepository.addNewWord(NewItem(language, newWord))
            .subscribeOn(Schedulers.io())
            .flatMap { wordResponse ->
                wordId = wordResponse.id
                wordsRepository.addTranslate(NewItem(translateLanguage, newTranslate))
            }
            .flatMap { translateResponse ->
                wordsRepository.addTranslateToWord(
                    wordId = wordId,
                    translateId = translateResponse.id
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
}