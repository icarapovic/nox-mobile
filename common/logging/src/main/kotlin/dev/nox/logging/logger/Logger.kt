package dev.nox.logging.logger

interface Logger {
    fun debug(message: () -> String)
    fun info(message: () -> String)
    fun warning(message: () -> String)
    fun error(e: Throwable?, message: () -> String)
}