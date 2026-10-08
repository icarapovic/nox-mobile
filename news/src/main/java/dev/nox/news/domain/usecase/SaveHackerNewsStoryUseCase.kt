package dev.nox.news.domain.usecase

import dev.nox.news.data.remote.hn.dto.HackerNewsStoryDto
import dev.nox.news.domain.models.HnCategory

interface SaveHackerNewsStoryUseCase {
    suspend fun save(story: HackerNewsStoryDto, category: HnCategory, rank: Int)
}