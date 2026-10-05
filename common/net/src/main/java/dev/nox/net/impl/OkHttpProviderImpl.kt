package dev.nox.net.impl

import dev.nox.net.OkHttpProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal class OkHttpProviderImpl(
    val loggingInterceptor: Interceptor
) : OkHttpProvider {

    override fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(20, TimeUnit.SECONDS)
            connectTimeout(20, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
        }.build()
    }
}