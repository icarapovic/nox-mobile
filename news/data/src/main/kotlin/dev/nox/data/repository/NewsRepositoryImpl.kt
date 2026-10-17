package dev.nox.data.repository

import dev.nox.data.local.dao.HackerNewsDao
import dev.nox.data.local.entity.HackerNewsRankingEntity
import dev.nox.data.local.entity.HackerNewsStoryEntity
import dev.nox.data.remote.client.HackerNewsClient
import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.repository.NewsRepository
import dev.nox.news.domain.repository.NewsSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

private const val PARALLEL_STORY_REQUESTS = 3

class NewsRepositoryImpl(
    private val hnDao: HackerNewsDao,
    private val hnApi: HackerNewsClient
) : NewsRepository {

    override fun observeNews(source: NewsSource): Flow<List<NewsItem>> {
        when(source) {
            NewsSource.HN_TOP,
            NewsSource.HN_BEST,
            NewsSource.HN_NEW,
            NewsSource.HN_ASK,
            NewsSource.HN_JOB,
            NewsSource.HN_SHOW -> return hnDao.observeStories(source.name)
                // shared tables re-emit on other sources' writes; skip unchanged lists
                .distinctUntilChanged()
                .map { entityList ->
                    entityList.map { entity -> entity.toDomain() }
                }
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
                // steps: id list + each story + db write
                val steps = ids.size + 2
                emit(progress(steps, ++count))

                // collected sequentially, so the map is not accessed concurrently
                val stories = mutableMapOf<Long, HackerNewsStoryEntity>()
                ids.asFlow()
                    .flatMapMerge(PARALLEL_STORY_REQUESTS) { id ->
                        flow { emit(hnApi.fetchStory(id).getOrNull()) }
                    }
                    .collect { story ->
                        story?.let { stories[it.id] = it.toStoryEntity() }
                        emit(progress(steps, ++count))
                    }

                val failedIds = ids.filterNot { it in stories }
                if (failedIds.isNotEmpty()) {
                    hnDao.getStories(failedIds).associateByTo(stories) { it.id }
                }

                // keep showing old stories if nothing could be loaded
                if (stories.isNotEmpty() || ids.isEmpty()) {
                    val rankedIds = ids.withIndex().filter { (_, id) -> id in stories }
                    hnDao.replaceStories(
                        source = source.name,
                        stories = rankedIds.map { (_, id) -> stories.getValue(id) },
                        rankings = rankedIds.map { (rank, id) ->
                            HackerNewsRankingEntity(newsSource = source.name, rank = rank, storyId = id)
                        }
                    )
                }
                emit(progress(steps, ++count))
            },
            onFailure = {
                // processing done due to failure, db left untouched
                emit(100.0f)
            }
        )
    }

    private fun progress(steps: Int, count: Int): Float {
        return ((count.toFloat() / steps) * 100.0f)
            .coerceIn(0.0f..100.0f)
    }

}