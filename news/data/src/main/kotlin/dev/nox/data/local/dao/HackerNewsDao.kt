package dev.nox.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import dev.nox.data.local.entity.HnCommentEntity
import dev.nox.data.local.entity.HnStoryBookmarkEntity
import dev.nox.data.local.entity.HnStoryEntity
import dev.nox.news.domain.models.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface HackerNewsDao {

    @Query("SELECT * FROM hn_stories WHERE source = 'HN_TOP' ORDER BY rank")
    fun observeTopStories(): Flow<List<HnStoryEntity>>

    @Query("SELECT * FROM hn_stories WHERE source = 'HN_NEW' ORDER BY rank")
    fun observeNewStories(): Flow<List<HnStoryEntity>>

    @Query("SELECT * FROM hn_stories WHERE source = 'HN_BEST' ORDER BY rank")
    fun observeBestStories(): Flow<List<HnStoryEntity>>

    @Query("SELECT * FROM hn_stories WHERE source = 'HN_ASK' ORDER BY rank")
    fun observeAskStories(): Flow<List<HnStoryEntity>>

    @Query("SELECT * FROM hn_stories WHERE source = 'HN_SHOW' ORDER BY rank")
    fun observeShowStories(): Flow<List<HnStoryEntity>>

    @Query("SELECT * FROM hn_stories WHERE source = 'HN_JOBS' ORDER BY rank")
    fun observeJobsStories(): Flow<List<HnStoryEntity>>

    @Query("SELECT * FROM hn_stories WHERE id IN (:ids) AND source = :source")
    suspend fun getStories(source: Source, ids: List<Long>): List<HnStoryEntity>

    @Transaction
    suspend fun updateStoryCache(source: Source, newStories: List<HnStoryEntity>) {
        deleteAllStories(source)
        insertAll(newStories)
        deleteOrphanedComments()
    }

    @Query("DELETE FROM hn_stories WHERE source = :source")
    suspend fun deleteAllStories(source: Source)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stories: List<HnStoryEntity>)

    @Query("SELECT * FROM hn_stories WHERE id = :id LIMIT 1")
    suspend fun getStory(id: Long): HnStoryEntity?

    @Query("SELECT * FROM hn_comment WHERE story_id = :storyId ORDER BY position ASC")
    fun observeComments(storyId: Long): Flow<List<HnCommentEntity>>

    @Transaction
    suspend fun updateCommentCache(storyId: Long, newComments: List<HnCommentEntity>) {
        deleteComments(storyId)
        insertAllComments(newComments)
    }

    @Query("DELETE FROM hn_comment WHERE story_id = :storyId")
    suspend fun deleteComments(storyId: Long)

    @Query("DELETE FROM hn_comment WHERE story_id NOT IN (SELECT id FROM hn_stories)")
    suspend fun deleteOrphanedComments()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComments(comments: List<HnCommentEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBookmark(storyBookmarkEntity: HnStoryBookmarkEntity): Long

    @Query("SELECT * FROM hn_bookmarks ORDER BY bookmarked_at DESC")
    fun getBookmarks(): Flow<List<HnStoryBookmarkEntity>>

    @Query("DELETE FROM hn_bookmarks WHERE id = :id")
    suspend fun deleteBookmark(id: Long): Int

    @Query("SELECT EXISTS(SELECT 1 FROM hn_bookmarks WHERE id = :storyId)")
    fun isBookmarked(storyId: Long): Flow<Boolean>

}
