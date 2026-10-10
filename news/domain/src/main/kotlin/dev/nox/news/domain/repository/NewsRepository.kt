package dev.nox.news.domain.repository

import dev.nox.news.domain.models.NewsItem
import kotlinx.coroutines.flow.Flow

/**
 * Central repository for fetching and storing news items
 * from different sources
 * */
interface NewsRepository {

    /**
     * Load news for a specific [NewsSource]
     *
     * @param source the [NewsSource] from which to load news
     * @return An observable flow of [NewsItem]s
     * */
    suspend fun observeNews(source: NewsSource): Flow<List<NewsItem>>

    /**
     * Fetches the newest news from the [NewsSource]
     *
     * @param source the [NewsSource] from which to load news
     * */
    suspend fun refreshNews(source: NewsSource)
}