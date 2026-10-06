package dev.nox.news.data

import dev.nox.news.data.local.hn.HackerNewsDao
import dev.nox.news.data.local.hn.HackerNewsEntity
import dev.nox.news.data.local.hn.toDomain
import dev.nox.news.domain.models.HackerNewsItem
import dev.nox.news.domain.repository.NewsRepository
import dev.nox.news.domain.usecase.FetchHackerNewsTopStoriesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val hackerNewsDao: HackerNewsDao,
    private val fetchHackerNewsTopStoriesUseCase: FetchHackerNewsTopStoriesUseCase,
) : NewsRepository {

    override suspend fun observeHackerNewsTopStories(): Flow<List<HackerNewsItem>> {
        return hackerNewsDao.observeTopStories()
            .map { news -> news.map(transform = HackerNewsEntity::toDomain) }
    }

    override suspend fun fetchHackerNewsTopStories(storyCount: Int) {
        fetchHackerNewsTopStoriesUseCase.fetch(storyCount)
    }

}