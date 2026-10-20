package dev.nox.news.domain.repository

import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.models.github.GitHubRepository
import dev.nox.news.domain.models.github.GitHubRepositorySort
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

    /**
     * Fetches all comments of a story as a flat list in thread order
     *
     * @param storyId id of the story whose comments to load
     * @return the comments, or an empty list if fetching failed
     * */
    fun getHackerNewsComments(storyId: Long): Flow<List<HackerNewsComment>>

    /**
     * Searches GitHub repositories
     *
     * @param query free text to search for
     * @param language only return repositories written in this programming language
     * @param sort the sort criteria, or `null` for best match
     * @param descending sort direction, ignored if [sort] is `null`
     * @param page 1-based page number
     * @return the matching repositories, or a failure holding a
     * [dev.nox.news.domain.exceptions.DataFetchingException] or
     * [dev.nox.news.domain.exceptions.NoInternetException]
     * */
    suspend fun searchGitHubRepositories(
        query: String,
        language: String? = null,
        sort: GitHubRepositorySort? = null,
        descending: Boolean = true,
        page: Int = 1,
        perPage: Int = 30
    ): Result<List<GitHubRepository>>
}
