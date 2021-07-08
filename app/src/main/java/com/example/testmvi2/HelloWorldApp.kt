package com.example.testmvi2

import android.app.Application
import timber.log.BuildConfig
import timber.log.Timber

class HelloWorldApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}