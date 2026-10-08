package dev.nox.news.domain.usecase

interface CheckHackerNewsStoryCached {
    suspend fun isCached(id: Long): Boolean
}