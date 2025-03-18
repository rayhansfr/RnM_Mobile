package com.example.rnmmobile

import android.app.Application
import com.example.core.di.coreModule
import com.example.rnmmobile.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(appModule, coreModule))
        }
    }
}