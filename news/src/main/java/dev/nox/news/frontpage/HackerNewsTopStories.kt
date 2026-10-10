package dev.nox.news.frontpage

import dev.nox.news.domain.models.hackernews.HackerNewsItem

data class HackerNewsTopStories (
    val stories: List<HackerNewsItem> = emptyList(),
    val progress: Float = 0.0f
)