package dev.nox.logging

import dev.nox.logging.logger.Logger
import dev.nox.logging.logger.LoggerImpl
import dev.nox.logging.perf.PerfLogger
import dev.nox.logging.perf.PerfLoggerImpl
import org.koin.dsl.module
import timber.log.Timber

val loggingModule = module {
    single<Logger> { LoggerImpl() }
    single<PerfLogger> { PerfLoggerImpl(get()) }
}