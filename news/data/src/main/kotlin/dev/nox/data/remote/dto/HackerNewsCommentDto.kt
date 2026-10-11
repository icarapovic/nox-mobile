package dev.nox.data.remote.dto

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class HackerNewsCommentDto(
    val id: Long,
    val text: String? = null,
    val by: String? = null,
    val time: Instant,
    val kids: List<Long> = listOf()
)