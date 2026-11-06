package dev.nox.news.domain.models.hackernews

data class HnStory (
    val id: Long,
    val title: String,
    val description: String?,
    val author: String,
    val timestamp: Long,
    val url: String?,
    val storyType: String,
    val comments: List<Long>,
    val score: Int,
    val commentCount: Int,
)
