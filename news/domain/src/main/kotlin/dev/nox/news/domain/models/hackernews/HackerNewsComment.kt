package dev.nox.news.domain.models.hackernews

data class HackerNewsComment (
    val id: Long,
    val text: String? = null,
    val by: String? = null,
    val time: Long,
    val parent: Long?,
    val depth: Int
)
