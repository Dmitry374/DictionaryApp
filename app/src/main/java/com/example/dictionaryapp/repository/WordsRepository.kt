package com.example.dictionaryapp.repository

import com.example.dictionaryapp.model.Translate
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.network.ApiService
import com.example.dictionaryapp.network.request.NewItem
import io.reactivex.Single

class WordsRepository(
    private val apiService: ApiService
) {

    fun loadAllWordsByLanguage(language: String): Single<List<Word>> {
        return apiService.loadAllWordsByLanguage(language)
    }

    fun searchWords(language: String, query: String): Single<List<Word>> {
        return apiService.searchWords(
            language = language,
            phrase = query
        )
    }

    fun addNewWord(newItem: NewItem): Single<Word> {
        return apiService.addNewWord(newItem)
    }

    fun addTranslate(newItem: NewItem): Single<Translate> {
        return apiService.addTranslate(newItem)
    }

    fun addTranslateToWord(wordId: Int, translateId: Int): Single<Word> {
        return apiService.addTranslateToWord(
            wordId = wordId,
            translateId = translateId
        )
    }

    fun deleteWord(id: Int): Single<Word> {
        return apiService.deleteWord(id)
    }
}