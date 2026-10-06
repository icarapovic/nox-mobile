package dev.nox.news.data.remote.hn.uc

import dev.nox.logging.perf.PerfLogger
import dev.nox.news.data.local.hn.HackerNewsDao
import dev.nox.news.data.remote.hn.dto.HackerNewsStoryDto
import dev.nox.news.data.remote.hn.dto.toEntity
import dev.nox.news.domain.usecase.SaveHackerNewsStoryUseCase

class SaveHackerNewsStoryUseCaseImpl(
    private val dao: HackerNewsDao,
    private val perfLogger: PerfLogger
) : SaveHackerNewsStoryUseCase {

    override suspend fun save(story: HackerNewsStoryDto) {
        perfLogger.measureSuspending("SHNS") {
            dao.insertStory(story.toEntity())
        }
    }
}