package dev.nox.news.domain.models.hackernews

import dev.nox.news.domain.models.NewsItem

data class HackerNewsItem (
    val id: Long,
    val title: String,
    val description: String?,
    val url: String?,
    val score: Int,
) : NewsItem