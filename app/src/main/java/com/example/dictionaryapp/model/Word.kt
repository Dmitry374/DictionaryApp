package com.example.dictionaryapp.model

data class Word(
    val id: Int,
    val language: String,
    val phrase: String,
    val translates: List<Translate>
)