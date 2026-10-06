package dev.nox.news.domain.repository

import dev.nox.news.domain.models.HackerNewsItem
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun observeHackerNewsTopStories(): Flow<List<HackerNewsItem>>

    suspend fun fetchHackerNewsTopStories(storyCount: Int)
}