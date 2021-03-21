package com.example.dictionaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    private val moviesFragment = WordsFragment()

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