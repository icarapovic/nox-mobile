package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import dev.nox.news.domain.models.hackernews.HnStory
import dev.nox.news.domain.models.hackernews.HnStoryBookmark

@Entity(tableName = "hn_bookmarks")
class HnStoryBookmarkEntity (
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String? = null,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "url") val url: String? = null,
    @ColumnInfo(name = "story_type") val storyType: String,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "comment_count") val commentCount: Int,
    @ColumnInfo(name = "bookmarked_at") val bookmarkedAt: Long
)

fun HnStoryBookmarkEntity.toBookmark() = HnStoryBookmark (
    this.id,
    this.title,
    this.content,
    this.author,
    this.timestamp,
    this.url,
    this.bookmarkedAt
)

fun HnStoryBookmarkEntity.toDomain() = HnStory(
    id = id,
    title = title,
    description = content,
    author = author,
    timestamp = timestamp,
    url = url,
    storyType = storyType,
    comments = emptyList(),
    score = score,
    commentCount = commentCount
)

fun HnStory.toBookmarkEntity(bookmarkedAt: Long) = HnStoryBookmarkEntity (
    id = this.id,
    title = this.title,
    content = this.description,
    author = this.author,
    timestamp = this.timestamp,
    url = this.url,
    storyType = this.storyType,
    score = this.score,
    commentCount = this.commentCount,
    bookmarkedAt = bookmarkedAt
)
