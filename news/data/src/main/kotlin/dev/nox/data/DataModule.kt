package dev.nox.data

import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import dev.nox.data.local.dao.GitHubDao
import dev.nox.data.local.dao.HackerNewsDao
import dev.nox.data.local.db.NewsDatabase
import dev.nox.data.remote.client.AlgoliaClient
import dev.nox.data.remote.client.GitHubClient
import dev.nox.data.remote.client.HackerNewsClient
import dev.nox.data.repository.NewsRepositoryImpl
import dev.nox.news.domain.repository.NewsRepository
import org.koin.dsl.module

val dataModule = module {

    single<NewsDatabase> {
        Room.databaseBuilder<NewsDatabase>(
            context = get(),
            name = "news.db"
        ).setDriver(AndroidSQLiteDriver()).build()
    }
    single<HackerNewsDao> { get<NewsDatabase>().hackerNewsDao() }
    single<GitHubDao> { get<NewsDatabase>().gitHubDao() }

    single<HackerNewsClient> { HackerNewsClient(get(), get()) }
    single<AlgoliaClient> { AlgoliaClient(get(), get()) }
    single<GitHubClient> { GitHubClient(get(), get()) }

    single<NewsRepository> { NewsRepositoryImpl(get(), get(), get()) }

}