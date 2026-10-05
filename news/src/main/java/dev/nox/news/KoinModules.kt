package dev.nox.news

import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import dev.nox.net.BaseUrlProvider
import dev.nox.news.data.NewsRepository
import dev.nox.news.data.NewsRepositoryImpl
import dev.nox.news.data.local.DB_NAME
import dev.nox.news.data.local.NewsDao
import dev.nox.news.data.local.NewsDatabase
import dev.nox.news.data.remote.hn.HackerNewsBaseUrlProvider
import dev.nox.news.data.remote.hn.HackerNewsBaseUrlProvider.Companion.HN_NAME
import dev.nox.news.data.remote.hn.HackerNewsClient
import dev.nox.news.frontpage.FrontPageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val hackerNewsModule = module {
    single<BaseUrlProvider>(
        qualifier = named(HN_NAME)
    ) { HackerNewsBaseUrlProvider() }
    single<HackerNewsClient> {
        HackerNewsClient(
            baseUrlProvider = get(qualifier = named(HN_NAME)),
            okHttpHttp = get(),
            converterFactory = get()) }
}

val viewModelModule = module {
    viewModel<FrontPageViewModel> { FrontPageViewModel(newsRepository = get()) }
}

val databaseModule = module {
    single<NewsDao> {
        Room.databaseBuilder<NewsDatabase>(
            context = get(),
            DB_NAME
        ).setDriver(AndroidSQLiteDriver())
            .build().newsDao()
    }

    single<NewsRepository> { NewsRepositoryImpl(
        newsDao = get(),
        hackerNewsClient = get(),
        logger = get(),
        perfLogger = get()
    )}
}