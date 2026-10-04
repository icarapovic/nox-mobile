package dev.nox.logging.perf

interface PerfLogger {
    fun measure(key: String, block: () -> Unit)
}