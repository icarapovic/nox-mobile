package dev.nox.news.data.remote.hn.uc

import dev.nox.logging.logger.Logger
import dev.nox.logging.perf.PerfLogger
import dev.nox.news.data.remote.hn.client.HackerNewsClient
import dev.nox.news.data.remote.hn.dto.HackerNewsStoryDto
import dev.nox.news.domain.usecase.CheckHackerNewsStoryCached
import dev.nox.news.domain.usecase.FetchHackerNewsStoryUseCase
import dev.nox.news.domain.usecase.FetchHackerNewsTopStoriesUseCase
import dev.nox.news.domain.usecase.SaveHackerNewsStoryUseCase

class FetchHackerNewsTopStoriesUseCaseImpl(
    private val hackerNewsClient: HackerNewsClient,
    private val fetchHackerNewsStoryUseCase: FetchHackerNewsStoryUseCase,
    private val saveHackerNewsStoryUseCase: SaveHackerNewsStoryUseCase,
    private val checkHackerNewsStoryCached: CheckHackerNewsStoryCached,
    private val logger: Logger,
    private val perfLogger: PerfLogger,
) : FetchHackerNewsTopStoriesUseCase {

    override suspend fun fetch(storyCount: Int) {

        var topStoriesResponse: Result<List<Long>> = Result.failure(
            exception = UninitializedPropertyAccessException("Stories request didn't run")
        )

        perfLogger.measureSuspending("HNFTS") {
            topStoriesResponse = hackerNewsClient.fetchTopStories()
        }

        topStoriesResponse.fold(
            onSuccess = { storyIds ->
                storyIds
                    .take(50)
                    .filter { id ->
                        !checkHackerNewsStoryCached.isCached(id)
                    }.forEach { id ->
                        fetchHackerNewsStoryUseCase.fetch(storyId = id)?.let { story ->
                            saveHackerNewsStoryUseCase.save(story)
                        }
                    }
            },
            onFailure = { error ->
                logger.error(error) { "Failed to fetch HN stories: ${error.message}" }
            }
        )
    }


}