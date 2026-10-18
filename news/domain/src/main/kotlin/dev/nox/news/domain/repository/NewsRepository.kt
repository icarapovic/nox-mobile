package dev.nox.news.domain.repository

import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.models.hackernews.HackerNewsComment
import dev.nox.news.domain.models.hackernews.HackerNewsItem
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
    fun observeNews(source: NewsSource): Flow<List<NewsItem>>

    /**
     * Fetches the newest news from the [NewsSource]
     *
     * @param source the [NewsSource] from which to load news
     *
     * @return Float value between 0.0 and 100.0 representing
     * the progress of refreshing the news in background
     * */
    fun refreshNews(source: NewsSource, maxStoriesCount: Int): Flow<Float>

    fun getHackerNewsStory(source: NewsSource, id: Long) : Flow<HackerNewsItem?>

    fun getHackerNewsComments(ids: List<Long>): Flow<List<HackerNewsComment>>
}