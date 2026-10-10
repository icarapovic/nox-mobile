package dev.chapz.data

import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import dev.chapz.data.local.dao.HackerNewsDao
import dev.chapz.data.local.db.NewsDatabase
import dev.chapz.data.remote.client.HackerNewsClient
import dev.chapz.data.repository.NewsRepositoryImpl
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

    single<HackerNewsClient> { HackerNewsClient(get(), get()) }

    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }

}