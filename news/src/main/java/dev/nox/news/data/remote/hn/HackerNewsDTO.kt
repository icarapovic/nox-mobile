package dev.nox.news.data.remote.hn

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class HackerNewsStory(
    val id: Long,
    val title: String,
    val text: String? = null,
    val by: String,
    val time: Long,
    val url: String? = null,
    val type: String,
    val kids: List<Long> = listOf(),
    val score: Int? = null,
    val descendants: Int? = null
)

@Serializable
data class HackerNewsComment(
    val id: CommentId,
    // Comments can be deleted
    val text: String? = null,
    val by: String? = null,
    val time: Instant,
    val kids: List<Long> = listOf()
)

@Serializable
data class StoryId(val id: Long)

@Serializable
data class StoryUrl(val url: String)

@Serializable
data class CommentId(val id: Long)