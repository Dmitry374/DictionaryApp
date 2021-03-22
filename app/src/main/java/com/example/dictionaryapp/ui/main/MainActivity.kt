package com.example.dictionaryapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.ui.communication.FragmentCommunicationInterface
import com.example.dictionaryapp.ui.translate.TranslateFragment
import com.example.dictionaryapp.ui.words.WordsFragment
import com.example.dictionaryapp.ui.words.addword.AddNewWordDialogFragment

class MainActivity : AppCompatActivity(), FragmentCommunicationInterface {

    companion object {
        private const val ADD_NEW_WORD = "add_new_word"
    }

    private val fragmentManager = supportFragmentManager

    private val wordsFragment =
        WordsFragment()

    private val addNewWordDialogFragment = AddNewWordDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_container, wordsFragment)
                .commit()
        }
    }

    override fun onOpenWordTranslate(word: Word) {
        fragmentManager.beginTransaction()
            .add(R.id.nav_host_container, TranslateFragment.newInstance(word))
            .addToBackStack(null)
            .commit()
    }

    override fun addNewWord() {
        addNewWordDialogFragment.show(fragmentManager, ADD_NEW_WORD)
    }

    override fun onAddNewWord(word: String, translate: String) {
        wordsFragment.onAddNewWord(word, translate)
    }
}