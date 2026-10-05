package dev.nox.news

import dev.nox.net.BaseUrlProvider
import dev.nox.news.data.remote.hn.HackerNewsBaseUrlProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val hackerNewsModule = module {
    single<BaseUrlProvider>(named(HackerNewsBaseUrlProvider.HN_NAME)) { HackerNewsBaseUrlProvider() }
}