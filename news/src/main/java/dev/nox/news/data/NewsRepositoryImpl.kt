package dev.nox.news.data

import dev.nox.logging.logger.Logger
import dev.nox.logging.perf.PerfLogger
import dev.nox.news.data.local.NewsDao
import dev.nox.news.data.remote.hn.HackerNewsClient
import dev.nox.news.data.remote.hn.HackerNewsStory
import dev.nox.news.domain.NewsItem
import dev.nox.news.domain.NewsSource
import kotlin.Result

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val hackerNewsClient: HackerNewsClient,
    private val logger: Logger,
    private val perfLogger: PerfLogger
) : NewsRepository {

    override suspend fun getNews(): List<NewsItem> {
        val stories = mutableListOf<HackerNewsStory>()
        var storyResponse: Result<List<Long>> = Result.failure(
            exception = UninitializedPropertyAccessException("Stories request didn't run")
        )

        perfLogger.measureSuspending("Fetch new stories") {
            storyResponse = hackerNewsClient.fetchNewStories()
        }

        storyResponse.fold(
            onSuccess = { storyIds ->
                var story: Result<HackerNewsStory> = Result.failure(
                    exception = UninitializedPropertyAccessException("Stories request didn't run")
                )
                storyIds.take(10).forEach { storyId ->
                    perfLogger.measureSuspending("Fetch story") {
                        story = hackerNewsClient.fetchStory(storyId)
                    }

                    story.fold(
                        onSuccess = { story ->
                            // persist stories locally
                            stories.add(story)
                        },
                        onFailure = { error ->
                            logger.error(error) { "Failed to fetch story $storyId: ${error.message}" }
                        }
                    )
                }
            },
            onFailure = { error ->
                logger.error(error) { "Failed to fetch HN stories: ${error.message}" }
            }
        )

        return stories.map { NewsItem(it.id, NewsSource.HACKER_NEWS, it.title) }
    }

}