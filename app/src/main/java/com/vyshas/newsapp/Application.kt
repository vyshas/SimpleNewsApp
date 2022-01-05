package com.vyshas.newsapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.vyshas.newsapp.common.isNight
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDayNightMode()
    }

    private fun setupDayNightMode() {
        // Get UI mode and set
        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

}