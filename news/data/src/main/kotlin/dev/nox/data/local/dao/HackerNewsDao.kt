package dev.nox.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Upsert
import dev.nox.data.local.entity.HackerNewsCommentEntity
import dev.nox.data.local.entity.HackerNewsCommentFetchEntity
import dev.nox.data.local.entity.HackerNewsRankingEntity
import dev.nox.data.local.entity.HackerNewsStoryEntity
import dev.nox.data.local.model.HackerNewsRankedStoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HackerNewsDao {

    @Query(
        """
        SELECT hn_story.*, hn_ranking.news_source, hn_ranking.rank
        FROM hn_ranking
        INNER JOIN hn_story ON hn_story.id = hn_ranking.story_id
        WHERE hn_ranking.news_source = :source
        ORDER BY hn_ranking.rank ASC
        """
    )
    fun observeStories(source: String): Flow<List<HackerNewsRankedStoryEntity>>

    /**
     * Cached stories, used as fallback for stories that failed to fetch
     * */
    @Query("SELECT * FROM hn_story WHERE id IN (:ids)")
    suspend fun getStories(ids: List<Long>): List<HackerNewsStoryEntity>

    @Query(
        """
        SELECT hn_story.*, hn_ranking.news_source, hn_ranking.rank
        FROM hn_ranking
        INNER JOIN hn_story ON hn_story.id = hn_ranking.story_id
        WHERE hn_ranking.news_source = :source AND hn_story.id = :id
        """
    )
    suspend fun getStory(source: String, id: Long): HackerNewsRankedStoryEntity?

    /**
     * Atomically replaces the whole ranked list of [source], so observers
     * receive a single emission per refresh
     * */
    @Transaction
    suspend fun replaceStories(
        source: String,
        stories: List<HackerNewsStoryEntity>,
        rankings: List<HackerNewsRankingEntity>
    ) {
        upsertStories(stories)
        deleteRankings(source)
        insertRankings(rankings)
        deleteUnrankedStories()
    }

    // Upsert updates in place; REPLACE would delete the row and cascade to its rankings
    @Upsert
    suspend fun upsertStories(stories: List<HackerNewsStoryEntity>)

    @Insert
    suspend fun insertRankings(rankings: List<HackerNewsRankingEntity>)

    @Query("DELETE FROM hn_ranking WHERE news_source = :source")
    suspend fun deleteRankings(source: String)

    // cascades to the story's cached comments
    @Query("DELETE FROM hn_story WHERE id NOT IN (SELECT story_id FROM hn_ranking)")
    suspend fun deleteUnrankedStories()

    @Query("SELECT * FROM hn_comment WHERE story_id = :storyId ORDER BY position ASC")
    fun observeComments(storyId: Long): Flow<List<HackerNewsCommentEntity>>

    @Query("SELECT fetched_at FROM hn_comment_fetch WHERE story_id = :storyId")
    suspend fun getCommentsFetchedAt(storyId: Long): Long?

    /**
     * Atomically replaces all cached comments of [storyId]. Skipped if the
     * story is no longer cached, as the comments would violate the foreign key
     * */
    @Transaction
    suspend fun replaceComments(
        storyId: Long,
        comments: List<HackerNewsCommentEntity>,
        fetchedAt: Long
    ) {
        if (!storyExists(storyId)) return
        deleteComments(storyId)
        insertComments(comments)
        upsertCommentFetch(HackerNewsCommentFetchEntity(storyId = storyId, fetchedAt = fetchedAt))
    }

    @Query("SELECT EXISTS(SELECT 1 FROM hn_story WHERE id = :storyId)")
    suspend fun storyExists(storyId: Long): Boolean

    @Query("DELETE FROM hn_comment WHERE story_id = :storyId")
    suspend fun deleteComments(storyId: Long)

    @Insert
    suspend fun insertComments(comments: List<HackerNewsCommentEntity>)

    @Upsert
    suspend fun upsertCommentFetch(fetch: HackerNewsCommentFetchEntity)
}
