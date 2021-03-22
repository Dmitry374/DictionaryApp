package com.example.dictionaryapp.domain

import com.example.dictionaryapp.common.Constants
import com.example.dictionaryapp.model.Translate
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

    fun deleteWord(id: Int): Single<Word> {
        return wordsRepository.deleteWord(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addNewTranslate(word: Word, newItem: NewItem): Single<List<Translate>> {
        return wordsRepository.addTranslate(newItem)
            .subscribeOn(Schedulers.io())
            .flatMap { translate ->

                wordsRepository.addTranslateToWord(
                    wordId = word.id,
                    translateId = translate.id
                )
                    .map { resultWord ->
                        word.translates = resultWord.translates
                        word.translates
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
}