package dev.nox.logging

import android.app.Application
import dev.chapz.logging.BuildConfig
import timber.log.Timber

fun Application.setupLogging() {
    if(BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    }
}