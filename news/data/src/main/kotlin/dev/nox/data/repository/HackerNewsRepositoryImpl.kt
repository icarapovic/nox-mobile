package dev.nox.data.repository

import dev.nox.data.local.dao.HackerNewsDao
import dev.nox.data.local.entity.HnStoryEntity
import dev.nox.data.local.entity.toBookmark
import dev.nox.data.local.entity.toBookmarkEntity
import dev.nox.data.remote.client.HackerNewsClient
import dev.nox.data.remote.client.HnCommentsClient
import dev.nox.logging.logger.Logger
import dev.nox.news.domain.models.Source
import dev.nox.news.domain.models.hackernews.HnComment
import dev.nox.news.domain.models.hackernews.HnStory
import dev.nox.news.domain.models.hackernews.HnStoryBookmark
import dev.nox.news.domain.repository.HackerNewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Clock

internal class HackerNewsRepositoryImpl(
    private val hnDao: HackerNewsDao,
    private val hnApi: HackerNewsClient,
    private val hnCommentsApi: HnCommentsClient,
    private val logger: Logger,
    private val clock: Clock = Clock.System
) : HackerNewsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun refreshData(source: Source) = flow {
        val result = when(source) {
            Source.HN_TOP -> hnApi.fetchTopStories()
            Source.HN_NEW -> hnApi.fetchNewStories()
            Source.HN_BEST -> hnApi.fetchBestStories()
            Source.HN_ASK -> hnApi.fetchAskStories()
            Source.HN_SHOW -> hnApi.fetchShowStories()
            Source.HN_JOBS -> hnApi.fetchJobStories()
        }

        var count = 0
        result.fold(
            onSuccess = { idList ->
                val ids = idList.take(20)
                val steps = ids.size + 2
                emit(progress(steps, ++count))

                val stories = mutableMapOf<Long, HnStoryEntity>()
                ids.asFlow().flatMapMerge(5) { storyId ->
                    flow { emit(hnApi.fetchStory(storyId).getOrNull()) }
                }.collect { story ->
                    story?.let {
                        stories[it.id] = it.toStoryEntity(source.name, ids.indexOf(it.id))
                        emit(progress(steps, ++count))
                    }
                }

                val failedIds = ids.filterNot { it in stories }
                if(failedIds.isNotEmpty()) {
                    hnDao.getStories(source, failedIds).associateByTo(stories, { it.id }) {
                        it.copy(rank = ids.indexOf(it.id))
                    }
                }

                if (stories.isNotEmpty() || ids.isEmpty()) {
                    hnDao.updateStoryCache(source, stories.values.toList())
                }
                emit(progress(steps, ++count))
            },
            onFailure = {
                emit(100f)
            }
        )
    }

    override fun observeTopStories(): Flow<List<HnStory>> {
        return hnDao.observeTopStories().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override fun observeBestStories(): Flow<List<HnStory>> {
        return hnDao.observeBestStories().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override fun observeNewStories(): Flow<List<HnStory>> {
        return hnDao.observeNewStories().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override fun observeShowStories(): Flow<List<HnStory>> {
        return hnDao.observeShowStories().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override fun observeAskStories(): Flow<List<HnStory>> {
        return hnDao.observeAskStories().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override fun observeJobsStories(): Flow<List<HnStory>> {
        return hnDao.observeJobsStories().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    private fun progress(steps: Int, count: Int): Float {
        return ((count.toFloat() / steps) * 100.0f)
            .coerceIn(0.0f..100.0f)
    }

    override fun getStory(id: Long): Flow<HnStory?> = flow {
        emit(hnDao.getStory(id)?.toDomain())
    }

    override fun getStoryComments(id: Long): Flow<List<HnComment>> = channelFlow {
        launch { refreshComments(id) }
        hnDao.observeComments(id)
            .distinctUntilChanged()
            .collect { entityList -> send(entityList.map { entity -> entity.toDomain() }) }
    }

    private suspend fun refreshComments(storyId: Long) {
        hnCommentsApi.fetchItem(storyId).onSuccess { story ->
            hnDao.updateCommentCache(storyId, story.toCommentEntities())
        }
    }

    override suspend fun saveBookmark(story: HnStory): Boolean {
        return hnDao.addBookmark(story.toBookmarkEntity()) != -1L
    }

    override fun getBookmarks(): Flow<List<HnStoryBookmark>> = flow {
        hnDao.getBookmarks().collect { bookmarkEntities ->
            val bookmarks = bookmarkEntities.map { it.toBookmark() }
            emit(bookmarks)
        }
    }

    override suspend fun deleteBookmark(storyId: Long): Boolean {
        return hnDao.deleteBookmark(storyId) != 0
    }

    override fun isBookmarked(story: HnStory): Flow<Boolean> {
        return hnDao.isBookmarked(story.id)
    }
}