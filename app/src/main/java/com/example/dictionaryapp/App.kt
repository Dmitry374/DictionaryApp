package com.example.dictionaryapp

import android.app.Application
import com.example.dictionaryapp.di.AppComponent
import com.example.dictionaryapp.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .build()
    }
}