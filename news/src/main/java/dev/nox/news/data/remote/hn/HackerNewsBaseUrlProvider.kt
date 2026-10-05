package dev.nox.news.data.remote.hn

import dev.nox.net.BaseUrlProvider

class HackerNewsBaseUrlProvider : BaseUrlProvider {
    companion object {
        const val HN_NAME = "hn"
    }
    override fun provideBaseUrl() = "https://hacker-news.firebaseio.com/v0/"
}