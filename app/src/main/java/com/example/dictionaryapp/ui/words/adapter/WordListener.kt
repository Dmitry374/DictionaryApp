package com.example.dictionaryapp.ui.words.adapter

import com.example.dictionaryapp.model.Word

interface WordListener {
    fun deleteWord(word: Word)
}