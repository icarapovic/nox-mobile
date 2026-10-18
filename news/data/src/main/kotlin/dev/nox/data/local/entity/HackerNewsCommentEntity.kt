package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey
import dev.nox.news.domain.models.hackernews.HackerNewsComment

/**
 * Cached comment of a [HackerNewsStoryEntity]. Deleted together with its
 * story, so the cache is bounded by the ranked stories
 * */
@Entity(
    tableName = "hn_comment",
    foreignKeys = [
        ForeignKey(
            entity = HackerNewsStoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["story_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("story_id")]
)
data class HackerNewsCommentEntity(
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
    fun toDomain() = HackerNewsComment(
        id = id,
        text = text,
        by = author,
        time = timestamp,
        parent = parent,
        depth = depth
    )
}
