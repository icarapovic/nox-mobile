package dev.nox.logging.perf

import dev.nox.logging.logger.Logger
import kotlin.system.measureTimeMillis
import kotlin.time.Duration
import kotlin.time.TimedValue
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

internal class PerfLoggerImpl(private val logger: Logger) : PerfLogger {

    override suspend fun <T> measureSuspending(key: String, block: suspend () -> T): T {
        val (value, duration) = measureTimedValue { block.invoke() }
        logger.perf(key) { "${duration.inWholeMilliseconds}ms" }
        return value
    }

    override fun <T> measure(key: String, block: () -> T): T {
        val (value, duration) = measureTimedValue { block.invoke() }
        logger.perf(key) { "${duration.inWholeMilliseconds}ms" }
        return value
    }
}