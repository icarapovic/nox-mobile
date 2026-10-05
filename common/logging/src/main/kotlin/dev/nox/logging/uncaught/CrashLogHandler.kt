package dev.nox.logging.uncaught

import dev.nox.logging.logger.Logger
import java.lang.Thread.UncaughtExceptionHandler

class CrashLogHandler (
    private val logger: Logger,
    private val previousHandler: UncaughtExceptionHandler? = Thread.getDefaultUncaughtExceptionHandler()
) : UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            logger.error(throwable) {
                "App crashed: ${throwable.stackTraceToString()}"
            }
        } finally {
            previousHandler?.uncaughtException(thread, throwable)
        }
    }
}