package dev.nox.news

import dev.nox.net.BaseUrlProvider
import dev.nox.news.data.remote.hn.HackerNewsBaseUrlProvider
import dev.nox.news.data.remote.hn.HackerNewsBaseUrlProvider.Companion.HN_NAME
import dev.nox.news.data.remote.hn.HackerNewsClient
import dev.nox.news.frontpage.FrontPageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val hackerNewsModule = module {
    single<BaseUrlProvider>(named(HN_NAME)) { HackerNewsBaseUrlProvider() }
    single<HackerNewsClient> { HackerNewsClient(get(named(HN_NAME)), get(), get()) }
}

val viewModelModule = module {
    viewModel<FrontPageViewModel> { FrontPageViewModel(get()) }
}