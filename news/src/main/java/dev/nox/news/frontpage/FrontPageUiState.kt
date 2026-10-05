package dev.nox.news.frontpage

import dev.nox.news.data.remote.hn.HackerNewsStory

data class FrontPageUiState(
    val stories: List<HackerNewsStory> = emptyList(),
    val isSuccessful: Boolean = true,
    val errorMessage: String = ""
)