package dev.nox.data.repository

import dev.nox.data.local.dao.HackerNewsDao
import dev.nox.data.remote.client.HackerNewsClient
import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.repository.NewsRepository
import dev.nox.news.domain.repository.NewsSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlin.ranges.rangeTo

private const val PARALLEL_STORY_REQUESTS = 3

class NewsRepositoryImpl(
    private val hnDao: HackerNewsDao,
    private val hnApi: HackerNewsClient
) : NewsRepository {

    override fun observeNews(source: NewsSource): Flow<List<NewsItem>> {
        return hnDao.observeTopStories().map { entityList ->
            entityList.map { entity -> entity.toDomain() }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun refreshNews(source: NewsSource, maxStoriesCount: Int) = flow {
        var count = 0
        emit(0.0f)

        val result = when(source) {
            NewsSource.HN_TOP -> hnApi.fetchTopStories()
            NewsSource.HN_BEST -> hnApi.fetchBestStories()
            NewsSource.HN_NEW -> hnApi.fetchNewStories()
            NewsSource.HN_ASK -> hnApi.fetchAskStories()
            NewsSource.HN_JOB -> hnApi.fetchJobStories()
            NewsSource.HN_SHOW -> hnApi.fetchShowStories()
        }

        result.fold(
            onSuccess = { list ->
                val ids = list.take(maxStoriesCount)
                emit(progress(ids.size, ++count))

                ids.asFlow()
                    .flatMapMerge(PARALLEL_STORY_REQUESTS) { id ->
                        flow { emit(fetchHackerNewsStory(id, ids.indexOf(id))) }
                    }
                    .collect { emit(progress(ids.size, ++count)) }
            },
            onFailure = {
                // processing done due to failure
                emit(progress(maxStoriesCount, maxStoriesCount + 1))
            }
        )
    }

    private suspend fun fetchHackerNewsStory(id: Long, rank: Int): Boolean {
        return hnApi.fetchStory(id).fold(
            onSuccess = { story ->
                hnDao.deleteOldRankedStory(rank)
                hnDao.insertStory(story.toEntity(rank))
                true
            },
            onFailure = {
                false
            }
        )
    }

    private fun progress(storiesCount: Int, count: Int): Float {
        return (((count.toFloat() / (storiesCount + 1f))) * 100.0f)
            .coerceIn(0.0f..100.0f)
    }

}