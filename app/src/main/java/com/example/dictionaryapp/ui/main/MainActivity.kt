package com.example.dictionaryapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.ui.communication.FragmentCommunicationInterface
import com.example.dictionaryapp.ui.translate.TranslateFragment
import com.example.dictionaryapp.ui.translate.addtranslate.AddNewTranslateDialogFragment
import com.example.dictionaryapp.ui.words.WordsFragment
import com.example.dictionaryapp.ui.words.addword.AddNewWordDialogFragment

class MainActivity : AppCompatActivity(), FragmentCommunicationInterface {

    companion object {
        private const val ADD_NEW_WORD = "add_new_word"
        private const val ADD_NEW_TRANSLATE = "add_new_translate"
    }

    private val fragmentManager = supportFragmentManager

    private val wordsFragment =
        WordsFragment()

    private lateinit var translateFragment: TranslateFragment

    private val addNewWordDialogFragment = AddNewWordDialogFragment()

    private val addNewTranslateDialogFragment = AddNewTranslateDialogFragment()

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

        translateFragment = TranslateFragment.newInstance(word)

        fragmentManager.beginTransaction()
            .add(R.id.nav_host_container, translateFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun showAddNewWordDialog() {
        addNewWordDialogFragment.show(fragmentManager, ADD_NEW_WORD)
    }

    override fun onAddNewWord(word: String, translate: String) {
        wordsFragment.onAddNewWord(word, translate)
    }

    override fun showAddNewTranslateDialog() {
        addNewTranslateDialogFragment.show(fragmentManager, ADD_NEW_TRANSLATE)
    }

    override fun onAddNewTranslate(translate: String) {
        translateFragment.onAddNewTranslate(translate)
    }
}