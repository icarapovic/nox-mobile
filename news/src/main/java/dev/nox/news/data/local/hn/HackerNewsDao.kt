package dev.nox.news.data.local.hn

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HackerNewsDao {

    @Query("SELECT * FROM hacker_news")
    fun observeTopStories(): Flow<List<HackerNewsEntity>>

    @Insert
    suspend fun insertStory(topStory: HackerNewsEntity)
}