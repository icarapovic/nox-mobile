package dev.nox.news.frontpage

import dev.nox.news.domain.models.HackerNewsItem

data class FrontPageUiState(
    val stories: List<HackerNewsItem> = emptyList(),
    val isSuccessful: Boolean = true,
    val errorMessage: String = ""
)