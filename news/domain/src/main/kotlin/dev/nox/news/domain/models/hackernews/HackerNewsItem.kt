package dev.nox.news.domain.models.hackernews

import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.repository.NewsSource

data class HackerNewsItem (
    override val id: Long,
    override val title: String,
    override val newsSource: NewsSource,
    val description: String?,
    val author: String,
    val timestamp: Long,
    val url: String?,
    val storyType: String,
    val comments: List<Long>,
    val score: Int,
    val rank: Int,
    val descendants: Int,
) : NewsItem
