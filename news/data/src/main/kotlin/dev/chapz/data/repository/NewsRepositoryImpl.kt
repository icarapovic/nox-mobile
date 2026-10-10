package dev.chapz.data.repository

import dev.chapz.data.local.dao.HackerNewsDao
import dev.chapz.data.remote.client.HackerNewsClient
import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.repository.NewsRepository
import dev.nox.news.domain.repository.NewsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.ranges.rangeTo

class NewsRepositoryImpl(
    private val hnDao: HackerNewsDao,
    private val hnApi: HackerNewsClient
) : NewsRepository {

    override fun observeNews(source: NewsSource): Flow<List<NewsItem>> {
        return hnDao.observeTopStories().map { entityList ->
            entityList.map { entity -> entity.toDomain() }
        }
    }

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
                emit(progress(maxStoriesCount, ++count))

                list.take(maxStoriesCount).forEach { id ->
                    fetchHackerNewsStory(id)
                    emit(progress(maxStoriesCount, ++count))
                }
            },
            onFailure = {
                // processing done due to failure
                emit(progress(maxStoriesCount, maxStoriesCount + 1))
            }
        )
    }

    private suspend fun fetchHackerNewsStory(id: Long): Boolean {
        return hnApi.fetchStory(id).fold(
            onSuccess = { story ->
                hnDao.insertStory(story.toEntity())
                true
            },
            onFailure = {
                false
            }
        )
    }

    private fun progress(maxStoriesCount: Int, count: Int): Float {
        return (((count.toFloat() / (maxStoriesCount + 1f))) * 100.0f)
            .coerceIn(0.0f..100.0f)
    }

}