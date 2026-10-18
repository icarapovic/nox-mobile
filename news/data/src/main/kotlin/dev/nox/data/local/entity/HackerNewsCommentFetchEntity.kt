package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey

/**
 * When the comments of a [HackerNewsStoryEntity] were last fetched.
 * Kept separately so stories without comments are cached too
 * */
@Entity(
    tableName = "hn_comment_fetch",
    foreignKeys = [
        ForeignKey(
            entity = HackerNewsStoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["story_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HackerNewsCommentFetchEntity(
    @ColumnInfo(name = "story_id")
    @PrimaryKey
    val storyId: Long,
    @ColumnInfo(name = "fetched_at")
    val fetchedAt: Long
)
