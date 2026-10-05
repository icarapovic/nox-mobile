package dev.nox.net

import retrofit2.Converter

interface ConverterFactoryProvider {
    fun provideConverterFactory(): Converter.Factory
}