package dev.nox.news.domain.models.hackernews

data class HnStoryBookmark (
    val id: Long? = null,
    val title: String,
    val content: String? = null,
    val author: String,
    val timestamp: Long,
    val url: String? = null,
    val bookmarkedAt: Long
)