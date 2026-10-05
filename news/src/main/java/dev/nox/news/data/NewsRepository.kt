package dev.nox.news.data

import dev.nox.news.domain.NewsItem

interface NewsRepository {
    suspend fun getNews(): List<NewsItem>
}