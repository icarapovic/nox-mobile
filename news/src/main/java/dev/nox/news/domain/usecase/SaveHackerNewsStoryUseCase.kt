package dev.nox.news.domain.usecase

import dev.nox.news.data.remote.hn.dto.HackerNewsStoryDto

interface SaveHackerNewsStoryUseCase {
    suspend fun save(story: HackerNewsStoryDto)
}