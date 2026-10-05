package dev.nox.logging.perf

interface PerfLogger {
    suspend fun measureSuspending(key: String, block: suspend () -> Unit): Long
    fun measure(key: String, block: () -> Unit): Long
}