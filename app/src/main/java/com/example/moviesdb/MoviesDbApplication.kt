package com.example.moviesdb

import android.app.Application
import timber.log.Timber

/**
 * Created by PATINIK-CONT on 03-Oct-19.
 */
class MoviesDbApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}