package dev.nox.logging.logger

import timber.log.Timber

internal class LoggerImpl : Logger {
    override fun info(message: () -> String) = Timber.i(message.invoke())
    override fun warning(message: () -> String) = Timber.i(message.invoke())
    override fun error(e: Throwable, message: () -> String) = Timber.e(e, message.invoke())
}