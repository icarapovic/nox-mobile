package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import dev.nox.news.domain.models.hackernews.HnStory
import dev.nox.news.domain.models.hackernews.HnStoryBookmark

@Entity(tableName = "hn_bookmarks")
class HnStoryBookmarkEntity (
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String? = null,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "url") val url: String? = null,
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

fun HnStory.toBookmarkEntity() = HnStoryBookmarkEntity (
    this.id,
    this.title,
    this.description,
    this.author,
    this.timestamp,
    this.url,
    System.currentTimeMillis()
)