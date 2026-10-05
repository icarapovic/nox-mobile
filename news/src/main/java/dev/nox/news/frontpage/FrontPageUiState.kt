package dev.nox.news.frontpage

import dev.nox.news.domain.NewsItem

data class FrontPageUiState(
    val stories: List<NewsItem> = emptyList(),
    val isSuccessful: Boolean = true,
    val errorMessage: String = ""
)