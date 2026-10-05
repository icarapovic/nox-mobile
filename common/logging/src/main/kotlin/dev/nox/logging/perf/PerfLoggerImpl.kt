package dev.nox.logging.perf

import dev.nox.logging.logger.Logger
import kotlin.system.measureTimeMillis

internal class PerfLoggerImpl(private val logger: Logger) : PerfLogger {

    override suspend fun measureSuspending(key: String, block: suspend () -> Unit): Long {
        try {
            val timeMillis = measureTimeMillis { block.invoke() }
            logger.info { "[P] $key: ${timeMillis}ms" }
            return timeMillis
        } catch (e: Throwable) {
            logger.error(e) { "Failed to measure performance for '$key' due to an error." }
        }

        return -1
    }

    override fun measure(key: String, block: () -> Unit): Long {
        try {
            val timeMillis = measureTimeMillis { block.invoke() }
            logger.info { "[P] $key: ${timeMillis}ms" }
            return timeMillis
        } catch (e: Throwable) {
            logger.error(e) { "Failed to measure performance for '$key' due to an error." }
        }

        return -1
    }
}