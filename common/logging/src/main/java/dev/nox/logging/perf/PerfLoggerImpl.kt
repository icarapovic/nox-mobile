package dev.nox.logging.perf

import dev.nox.logging.logger.Logger
import kotlin.system.measureTimeMillis

class PerfLoggerImpl(private val logger: Logger) : PerfLogger {

    override fun measure(key: String, block: () -> Unit) {
        try {
            val timeMillis = measureTimeMillis { block.invoke() }
            logger.info { "[P] $key: ${timeMillis}ms" }
        } catch (e: Throwable) {
            logger.error(e) { "Failed to measure performance for '$key' due to an error." }
        }
    }
}