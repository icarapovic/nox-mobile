package dev.nox.news.data.remote.hn.dto

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class HackerNewsCommentDto(
    val id: CommentIdDto,
    // Comments can be deleted
    val text: String? = null,
    val by: String? = null,
    val time: Instant,
    val kids: List<Long> = listOf()
)

@Serializable
data class CommentIdDto(val id: Long)