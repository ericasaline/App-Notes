package com.app.notes

import android.app.Application
import com.app.notes.di.dataModule
import com.app.notes.di.databaseModule
import com.app.notes.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(databaseModule, dataModule, viewModelModule)
        }
    }
}