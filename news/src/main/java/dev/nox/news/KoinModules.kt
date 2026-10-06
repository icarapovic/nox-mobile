package dev.nox.news

import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import dev.nox.net.BaseUrlProvider
import dev.nox.news.domain.repository.NewsRepository
import dev.nox.news.data.NewsRepositoryImpl
import dev.nox.news.data.local.DB_NAME
import dev.nox.news.data.local.hn.HackerNewsDao
import dev.nox.news.data.local.NewsDatabase
import dev.nox.news.data.remote.hn.client.HackerNewsBaseUrlProvider
import dev.nox.news.data.remote.hn.client.HackerNewsBaseUrlProvider.Companion.HN_NAME
import dev.nox.news.data.remote.hn.client.HackerNewsClient
import dev.nox.news.data.remote.hn.uc.FetchHackerNewsStoryUseCaseImpl
import dev.nox.news.data.remote.hn.uc.FetchHackerNewsTopStoriesUseCaseImpl
import dev.nox.news.data.remote.hn.uc.SaveHackerNewsStoryUseCaseImpl
import dev.nox.news.domain.usecase.FetchHackerNewsStoryUseCase
import dev.nox.news.domain.usecase.FetchHackerNewsTopStoriesUseCase
import dev.nox.news.domain.usecase.SaveHackerNewsStoryUseCase
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
    viewModel<FrontPageViewModel> { FrontPageViewModel(
        newsRepository = get(),
        logger = get()
    ) }
}

val databaseModule = module {
    single<HackerNewsDao> {
        Room.databaseBuilder<NewsDatabase>(
            context = get(),
            DB_NAME
        ).setDriver(AndroidSQLiteDriver())
            .build().hackerNewsDao()
    }

    single<NewsRepository> { NewsRepositoryImpl(
        hackerNewsDao = get(),
        fetchHackerNewsTopStoriesUseCase = get()
    )}
}

val useCaseModule = module {
    single<SaveHackerNewsStoryUseCase> { SaveHackerNewsStoryUseCaseImpl(
        dao = get(),
        perfLogger = get()
    ) }
    single<FetchHackerNewsStoryUseCase> { FetchHackerNewsStoryUseCaseImpl(
        hackerNewsClient = get(),
        logger = get(),
        perfLogger = get()
    )}
    single<FetchHackerNewsTopStoriesUseCase> { FetchHackerNewsTopStoriesUseCaseImpl(
        hackerNewsClient = get(),
        fetchHackerNewsStoryUseCase = get(),
        saveHackerNewsStoryUseCase = get(),
        logger = get(),
        perfLogger = get()
    )}
}