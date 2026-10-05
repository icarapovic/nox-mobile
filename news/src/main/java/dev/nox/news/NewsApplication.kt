package dev.nox.news

import android.app.Application
import dev.nox.logging.loggingModule
import dev.nox.net.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            modules(
                loggingModule,
                networkModule,
                databaseModule,
                hackerNewsModule,
                viewModelModule
            )
        }
    }
}