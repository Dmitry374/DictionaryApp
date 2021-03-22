package com.example.dictionaryapp.ui.communication

import com.example.dictionaryapp.model.Word

interface FragmentCommunicationInterface {
    fun onOpenWordTranslate(word: Word)

    fun addNewWord()
    fun onAddNewWord(word: String, translate: String)

    fun showAddNewTranslateDialog()
    fun onAddNewTranslate(translate: String)
}