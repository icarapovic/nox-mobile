package dev.nox.data.local.db

import androidx.room3.Database
import androidx.room3.RoomDatabase
import dev.nox.data.local.dao.HackerNewsDao
import dev.nox.data.local.entity.HnCommentEntity
import dev.nox.data.local.entity.HnStoryBookmarkEntity
import dev.nox.data.local.entity.HnStoryEntity

@Database(
    entities = [
        HnStoryEntity::class,
        HnCommentEntity::class,
        HnStoryBookmarkEntity::class
    ],
    version = 1
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun hackerNewsDao(): HackerNewsDao
}