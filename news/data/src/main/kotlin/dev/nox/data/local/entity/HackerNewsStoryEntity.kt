package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

/**
 * Content of a single Hacker News story, stored once regardless of
 * how many [HackerNewsRankingEntity] rows reference it
 * */
@Entity(tableName = "hn_story")
data class HackerNewsStoryEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Long,
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
)
