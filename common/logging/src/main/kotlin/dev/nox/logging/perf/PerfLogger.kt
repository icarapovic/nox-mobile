package dev.nox.logging.perf

import kotlin.time.TimedValue

interface PerfLogger {
    suspend fun <T> measureSuspending(key: String, block: suspend () -> T): T

    fun <T> measure(key: String, block: () -> T): T
}