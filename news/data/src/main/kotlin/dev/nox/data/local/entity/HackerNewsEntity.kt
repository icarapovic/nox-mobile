package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import dev.nox.news.domain.repository.NewsSource

@Entity(tableName = "hacker_news")
data class HackerNewsEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Long? = null,
    @ColumnInfo(name = "news_source")
    val newsSource: String,
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
    @ColumnInfo(name = "rank")
    val rank: Int,
    @ColumnInfo(name = "descendants")
    val descendants: Int? = null
) {
    fun toDomain() = HackerNewsItem(
        id = this.id ?: 0L,
        title = this.title,
        newsSource = NewsSource.valueOf(newsSource),
        description = this.text,
        author = this.author,
        timestamp = this.timestamp,
        url = this.url,
        storyType = this.storyType,
        comments = this.comments.split(";").mapNotNull { it.toLongOrNull() },
        score = this.score ?: 0,
        rank = this.rank,
        descendants = this.descendants ?: 0,
    )
}