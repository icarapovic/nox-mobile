package dev.nox.data

import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import dev.nox.data.local.dao.HackerNewsDao
import dev.nox.data.local.db.NewsDatabase
import dev.nox.data.remote.client.HackerNewsClient
import dev.nox.data.remote.client.HnCommentsClient
import dev.nox.data.repository.HackerNewsRepositoryImpl
import dev.nox.news.domain.repository.HackerNewsRepository
import org.koin.dsl.module
import kotlin.time.Clock

val dataModule = module {

    single<NewsDatabase> {
        Room.databaseBuilder<NewsDatabase>(
            context = get(),
            name = "news.db"
        ).setDriver(AndroidSQLiteDriver()).build()
    }
    single<HackerNewsDao> { get<NewsDatabase>().hackerNewsDao() }
    single<Clock> { Clock.System }

    single<HackerNewsClient> { HackerNewsClient(get(), get()) }
    single<HnCommentsClient> { HnCommentsClient(get(), get()) }
    single<HackerNewsRepository> { HackerNewsRepositoryImpl(get(), get(), get(), get()) }
}