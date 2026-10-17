package dev.nox.data.local.model

import androidx.room3.ColumnInfo
import androidx.room3.Embedded
import dev.nox.data.local.entity.HackerNewsStoryEntity
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import dev.nox.news.domain.repository.NewsSource

/**
 * Query result joining a [HackerNewsStoryEntity] with its rank in a news source
 * */
data class HackerNewsRankedStory(
    @Embedded
    val story: HackerNewsStoryEntity,
    @ColumnInfo(name = "news_source")
    val newsSource: String,
    @ColumnInfo(name = "rank")
    val rank: Int
) {
    fun toDomain() = HackerNewsItem(
        id = story.id,
        title = story.title,
        newsSource = NewsSource.valueOf(newsSource),
        description = story.text,
        author = story.author,
        timestamp = story.timestamp,
        url = story.url,
        storyType = story.storyType,
        comments = story.comments.split(";").mapNotNull { it.toLongOrNull() },
        score = story.score ?: 0,
        rank = rank,
        descendants = story.descendants ?: 0,
    )
}
