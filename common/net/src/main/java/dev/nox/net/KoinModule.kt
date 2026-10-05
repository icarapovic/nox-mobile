package dev.nox.net

import dev.nox.net.impl.ConverterFactoryProviderImpl
import dev.nox.net.impl.OkHttpProviderImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.dsl.module
import retrofit2.Converter

val networkModule = module {
    single<Interceptor> { HttpLoggingInterceptor().apply { level = Level.NONE } }
    single<Converter.Factory> { ConverterFactoryProviderImpl().provideConverterFactory() }
    single<OkHttpClient> { OkHttpProviderImpl(get()).provideOkHttp() }
}