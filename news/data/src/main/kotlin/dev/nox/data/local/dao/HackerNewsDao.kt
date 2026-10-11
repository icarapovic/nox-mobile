package dev.nox.data.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import dev.nox.data.local.entity.HackerNewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HackerNewsDao {

    @Query("SELECT * FROM hacker_news")
    fun observeTopStories(): Flow<List<HackerNewsEntity>>

    @Upsert
    suspend fun insertStory(topStory: HackerNewsEntity)
}