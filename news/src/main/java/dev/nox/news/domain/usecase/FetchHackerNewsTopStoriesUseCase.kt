package dev.nox.news.domain.usecase

interface FetchHackerNewsTopStoriesUseCase {
    suspend fun fetch(storyCount: Int)
}