package dev.nox.news.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase
import dev.nox.news.data.local.entity.HackerNewsEntity

const val DB_NAME = "NewsDatabase"

@Database(entities = [HackerNewsEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}