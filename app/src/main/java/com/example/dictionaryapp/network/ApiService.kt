package com.example.dictionaryapp.network

import com.example.dictionaryapp.model.Word
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("word/language")
    fun loadAllWordsByLanguage(
        @Query("language") language: String
    ): Single<List<Word>>

    @GET("word/language")
    fun searchWords(
        @Query("language") language: String,
        @Query("phrase") phrase: String
    ): Single<List<Word>>
}