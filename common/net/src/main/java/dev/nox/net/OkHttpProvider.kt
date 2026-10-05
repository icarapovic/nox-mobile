package dev.nox.net

import okhttp3.OkHttpClient

interface OkHttpProvider {
    fun provideOkHttp(): OkHttpClient
}