package dev.nox.data.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Upsert
import dev.nox.data.local.entity.HackerNewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HackerNewsDao {

    @Query("SELECT * FROM hacker_news WHERE hacker_news.news_source = :source")
    fun observeStories(source: String): Flow<List<HackerNewsEntity>>

    @Transaction
    suspend fun putStory(story: HackerNewsEntity) {
        deleteStoryAtRank(story.newsSource, story.rank)
        upsertStory(story)
    }

    @Upsert
    suspend fun upsertStory(story: HackerNewsEntity)

    @Query("DELETE FROM hacker_news WHERE hacker_news.news_source = :source AND hacker_news.rank = :rank")
    suspend fun deleteStoryAtRank(source: String, rank: Int)
}
