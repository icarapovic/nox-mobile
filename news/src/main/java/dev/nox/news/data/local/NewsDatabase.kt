package dev.nox.news.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase
import dev.nox.news.data.local.hn.HackerNewsDao
import dev.nox.news.data.local.hn.HackerNewsEntity

const val DB_NAME = "news.db"

@Database(entities = [HackerNewsEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun hackerNewsDao(): HackerNewsDao
}