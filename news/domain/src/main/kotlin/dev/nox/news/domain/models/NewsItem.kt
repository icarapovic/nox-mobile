package dev.nox.news.domain.models

import dev.nox.news.domain.repository.NewsSource

/**
 * Marker interface for generally any news item
 * */
interface NewsItem {
    val id: Long
    val title: String
    val newsSource: NewsSource
}
