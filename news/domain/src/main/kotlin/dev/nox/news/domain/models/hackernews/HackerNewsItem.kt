package dev.nox.news.domain.models.hackernews

import dev.nox.news.domain.models.NewsItem

data class HackerNewsItem (
    val id: Long,
    val title: String,
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
