package dev.nox.news.domain

data class NewsItem(
    val id: Long,
    val newsSource: NewsSource,
    val title: String
)
