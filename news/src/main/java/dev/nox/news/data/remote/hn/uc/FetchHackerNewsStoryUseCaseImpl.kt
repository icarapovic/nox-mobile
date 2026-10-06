package dev.nox.news.data.remote.hn.uc

import dev.nox.logging.logger.Logger
import dev.nox.logging.perf.PerfLogger
import dev.nox.news.data.remote.hn.client.HackerNewsClient
import dev.nox.news.data.remote.hn.dto.HackerNewsStoryDto
import dev.nox.news.domain.usecase.FetchHackerNewsStoryUseCase

class FetchHackerNewsStoryUseCaseImpl (
    private val hackerNewsClient: HackerNewsClient,
    private val logger: Logger,
    private val perfLogger: PerfLogger
) : FetchHackerNewsStoryUseCase {

    override suspend fun fetch(storyId: Long): HackerNewsStoryDto? {

        var story: Result<HackerNewsStoryDto> = Result.failure(
            exception = UninitializedPropertyAccessException("Stories request didn't run")
        )

        perfLogger.measureSuspending("Fetch story") {
            story = hackerNewsClient.fetchStory(storyId)
        }

        story.fold(
            onSuccess = { story ->
                return story
            },
            onFailure = { error ->
                logger.error(error) { "Failed to fetch story $storyId: ${error.message}" }
                return null
            }
        )
    }
}