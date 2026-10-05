package dev.nox.logging.logger

interface Logger {
    fun info(message: () -> String)
    fun warning(message: () -> String)
    fun error(e: Throwable?, message: () -> String)
}