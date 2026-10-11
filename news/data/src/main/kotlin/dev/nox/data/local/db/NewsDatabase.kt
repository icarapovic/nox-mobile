package dev.nox.data.local.db

import androidx.room3.Database
import androidx.room3.RoomDatabase
import dev.nox.data.local.dao.HackerNewsDao
import dev.nox.data.local.entity.HackerNewsEntity

@Database(entities = [HackerNewsEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun hackerNewsDao(): HackerNewsDao
}