package br.com.renatoarg.weatherapp2021

import android.app.Application
import com.airbnb.mvrx.Mavericks
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Koin
        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(
                homeModule
            )
        }

        Mavericks.initialize(this)
    }
}