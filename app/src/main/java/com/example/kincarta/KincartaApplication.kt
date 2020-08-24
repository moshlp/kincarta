package com.example.kincarta

import android.app.Application
import com.example.kincarta.di.kcModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KincartaApplication : Application() {

    fun KincartaApplication() {
        instance = this
    }
    companion object {
        private var instance: KincartaApplication? = null
        fun getContext(): KincartaApplication? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin {
            androidContext(this@KincartaApplication)
            androidLogger(Level.DEBUG)
            modules(kcModule)
        }
    }
}