package com.braiso_22.bichota_timer

import android.app.Application
import com.braiso_22.bichota_timer.di.initKoin
import org.koin.android.ext.koin.androidContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@App)
        }
    }
}