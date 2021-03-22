package com.example.dictionaryapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.ui.communication.FragmentCommunicationInterface
import com.example.dictionaryapp.ui.translate.TranslateFragment
import com.example.dictionaryapp.ui.words.WordsFragment

class MainActivity : AppCompatActivity(), FragmentCommunicationInterface {

    private val fragmentManager = supportFragmentManager

    private val moviesFragment =
        WordsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_container, moviesFragment)
                .commit()
        }
    }

    override fun onOpenWordTranslate(word: Word) {
        fragmentManager.beginTransaction()
            .add(R.id.nav_host_container, TranslateFragment.newInstance(word))
            .addToBackStack(null)
            .commit()
    }
}