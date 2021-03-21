package com.example.dictionaryapp.repository

import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.network.ApiService
import io.reactivex.Single

class WordsRepository(
    private val apiService: ApiService
) {

    fun loadAllWordsByLanguage(language: String): Single<List<Word>> {
        return apiService.loadAllWordsByLanguage(language)
    }
}