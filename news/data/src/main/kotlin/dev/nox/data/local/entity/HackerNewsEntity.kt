package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import dev.nox.news.domain.models.hackernews.HackerNewsItem

@Entity(tableName = "hacker_news")
data class HackerNewsEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Long? = null,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val text: String? = null,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "url")
    val url: String? = null,
    @ColumnInfo(name = "story_type")
    val storyType: String,
    @ColumnInfo(name = "comment_ids")
    val comments: String,
    @ColumnInfo(name = "score")
    val score: Int? = null,
    @ColumnInfo(name = "descendants")
    val descendants: Int? = null
) {
    fun toDomain() = HackerNewsItem(
        id = this.id ?: 0L,
        title = this.title,
        description = this.text,
        url = this.url,
        score = this.score ?: 0,
    )
}