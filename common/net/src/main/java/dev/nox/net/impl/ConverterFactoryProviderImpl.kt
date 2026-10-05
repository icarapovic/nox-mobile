package dev.nox.net.impl

import dev.nox.net.ConverterFactoryProvider
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal class ConverterFactoryProviderImpl(
    val mediaType: MediaType = "application/json".toMediaType(),
): ConverterFactoryProvider {

    override fun provideConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            useAlternativeNames = true
        }

        return json.asConverterFactory(mediaType)
    }
}