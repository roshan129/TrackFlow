package com.roshanadke.trackflow

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.roshanadke.trackflow.common.Constants
import com.roshanadke.trackflow.data.local.TrackFlowPreferences
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TrackFlowApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        TrackFlowPreferences.initialize(applicationContext)
        Timber.plant(Timber.DebugTree())

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.CHANNEL_ID,
                getString(R.string.tracking_location),
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}