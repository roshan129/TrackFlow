package com.roshanadke.trackflow

import android.app.Application
import com.roshanadke.trackflow.data.local.TrackFlowPreferences
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TrackFlowApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        TrackFlowPreferences.initialize(applicationContext)
        Timber.plant(Timber.DebugTree())
    }
}