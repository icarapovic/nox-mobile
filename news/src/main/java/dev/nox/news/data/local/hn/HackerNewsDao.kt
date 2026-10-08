package dev.nox.news.data.local.hn

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HackerNewsDao {

    @Query("SELECT * FROM hacker_news")
    fun observeTopStories(): Flow<List<HackerNewsEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM hacker_news WHERE id = :id")
    suspend fun containsStory(id: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStory(topStory: HackerNewsEntity)
}