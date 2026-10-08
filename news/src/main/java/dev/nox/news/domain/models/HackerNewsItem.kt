package dev.nox.news.domain.models

import dev.nox.news.domain.repository.NewsSource

data class HackerNewsItem (
    val newsSource: NewsSource,
    val title: String,
    val description: String?,
    val url: String?,
    val score: Int,
    val rank: Int
)

enum class HnCategory {
    TOP, NEW, ASK, SHOW, JOB
}
