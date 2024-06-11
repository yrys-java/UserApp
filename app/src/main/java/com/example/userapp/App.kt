package com.example.userapp

import android.app.Application
import com.example.userapp.core.db.DatabaseModule
import com.example.userapp.core.di.CoreModule
import com.example.userapp.core.network.di.NetworkModule
import com.example.userapp.user.di.UserModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupKoin()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupKoin() {
        GlobalContext.stopKoin()
        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@App)
            modules(
                CoreModule.create(),
                NetworkModule.create(),
                DatabaseModule.create(),
                UserModule.create()
            )
        }
    }
}