package com.example.dictionaryapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dictionaryapp.R
import com.example.dictionaryapp.ui.words.WordsFragment

class MainActivity : AppCompatActivity() {

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
}