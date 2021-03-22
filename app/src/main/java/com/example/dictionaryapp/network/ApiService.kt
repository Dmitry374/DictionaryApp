package com.example.dictionaryapp.network

import com.example.dictionaryapp.model.Translate
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.network.request.NewItem
import io.reactivex.Single
import retrofit2.http.*

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

    @POST("word")
    fun addNewWord(@Body newItem: NewItem): Single<Word>

    @POST("translate")
    fun addTranslate(@Body newItem: NewItem): Single<Translate>

    @POST("word/{wordId}/translates/{translateId}/add")
    fun addTranslateToWord(
        @Path("wordId") wordId: Int,
        @Path("translateId") translateId: Int
    ): Single<Word>
}