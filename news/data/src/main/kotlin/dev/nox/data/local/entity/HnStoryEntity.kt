package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Index
import dev.nox.news.domain.models.hackernews.HnStory

@Entity(
    tableName = "hn_stories",
    primaryKeys = ["id", "source"],
    indices = [Index(value = ["source", "rank"], unique = true)]
)
data class HnStoryEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String? = null,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "rank") val rank: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "url") val url: String? = null,
    @ColumnInfo(name = "story_type") val storyType: String,
    @ColumnInfo(name = "comment_ids") val comments: String,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "comment_count") val commentCount: Int,
    @ColumnInfo(name = "source") val source: String
) {
    fun toDomain(): HnStory {
        return HnStory(
            id = id,
            title = title,
            description = content,
            author = author,
            timestamp = timestamp,
            url = url,
            storyType = storyType,
            comments = comments.split(";").filter { it.isNotBlank() }.map { it.toLong() },
            score = score,
            commentCount = commentCount
        )
    }
}
