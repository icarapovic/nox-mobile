package dev.nox.data.local.db

import androidx.room3.Database
import androidx.room3.RoomDatabase
import dev.nox.data.local.dao.HackerNewsDao
import dev.nox.data.local.entity.HackerNewsCommentEntity
import dev.nox.data.local.entity.HackerNewsCommentFetchEntity
import dev.nox.data.local.entity.HackerNewsRankingEntity
import dev.nox.data.local.entity.HackerNewsStoryEntity

@Database(
    entities = [
        HackerNewsStoryEntity::class,
        HackerNewsRankingEntity::class,
        HackerNewsCommentEntity::class,
        HackerNewsCommentFetchEntity::class
    ],
    version = 1
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun hackerNewsDao(): HackerNewsDao
}