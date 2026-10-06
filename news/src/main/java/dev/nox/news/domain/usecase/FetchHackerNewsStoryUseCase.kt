package dev.nox.news.domain.usecase

import dev.nox.news.data.remote.hn.dto.HackerNewsStoryDto

interface FetchHackerNewsStoryUseCase {
    suspend fun fetch(storyId: Long): HackerNewsStoryDto?
}