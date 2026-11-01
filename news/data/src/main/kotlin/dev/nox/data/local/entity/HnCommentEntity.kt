package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import dev.nox.news.domain.models.hackernews.HnComment

/**
 * Cached comment of a [HnStoryEntity]. Has no foreign key because a story has
 * one row per source and those rows are replaced on refresh. Deleted by
 * [dev.nox.data.local.dao.HackerNewsDao.updateStoryCache] once its story has no
 * rows left, so the cache is bounded by the ranked stories
 * */
@Entity(
    tableName = "hn_comment",
    indices = [Index("story_id")]
)
data class HnCommentEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "story_id")
    val storyId: Long,
    // index in the flattened thread, keeps the comment order
    @ColumnInfo(name = "position")
    val position: Int,
    @ColumnInfo(name = "depth")
    val depth: Int,
    @ColumnInfo(name = "parent")
    val parent: Long?,
    @ColumnInfo(name = "text")
    val text: String?,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long
) {
    fun toDomain() = HnComment(
        id = id,
        text = text,
        by = author,
        time = timestamp,
        parent = parent,
        depth = depth
    )
}
