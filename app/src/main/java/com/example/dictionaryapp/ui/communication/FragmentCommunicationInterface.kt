package com.example.dictionaryapp.ui.communication

import com.example.dictionaryapp.model.Word

interface FragmentCommunicationInterface {
    fun onOpenWordTranslate(word: Word)

    fun showAddNewWordDialog()
    fun onAddNewWord(word: String, translate: String)

    fun showAddNewTranslateDialog()
    fun onAddNewTranslate(translate: String)
}