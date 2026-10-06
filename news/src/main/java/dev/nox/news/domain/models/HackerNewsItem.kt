package dev.nox.news.domain.models

import dev.nox.news.domain.repository.NewsSource

data class HackerNewsItem (
    val id: Long,
    val newsSource: NewsSource,
    val title: String
)
