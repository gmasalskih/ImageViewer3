package ru.gmasalskikh.imageviewer3

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.gmasalskikh.imageviewer3.di.viewModelModules

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(viewModelModules)
        }
    }
}