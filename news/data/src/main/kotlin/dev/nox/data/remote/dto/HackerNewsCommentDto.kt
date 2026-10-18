package dev.nox.data.remote.dto

import dev.nox.news.domain.models.hackernews.HackerNewsComment
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class HackerNewsCommentDto(
    val id: Long,
    val text: String? = null,
    val by: String? = null,
    val time: Long,
    val parent: Long?,
    val kids: List<Long> = listOf()
) {

    fun toDomain() = HackerNewsComment(
        id = id,
        text = text,
        by = by,
        time = time,
        parent = parent,
        kids = kids
    )
}