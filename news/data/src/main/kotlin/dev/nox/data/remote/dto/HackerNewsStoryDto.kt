package dev.nox.data.remote.dto

import dev.nox.data.local.entity.HnStoryEntity
import kotlinx.serialization.Serializable

@Serializable
data class HackerNewsStoryDto(
    val by: String,
    val descendants: Int? = null,
    val id: Long,
    val kids: List<Long> = listOf(),
    val score: Int? = null,
    val time: Long,
    val title: String,
    val type: String,
    val url: String? = null,
    val text: String? = null,
    val dead: Boolean? = false
) {
    fun toStoryEntity(source: String, rank: Int) = HnStoryEntity(
        id = id,
        title = title,
        content = text,
        author = by,
        timestamp = time,
        url = url,
        storyType = type,
        comments = kids.joinToString(";"),
        score = score ?: 0,
        rank = rank,
        commentCount = descendants ?: 0,
        source = source
    )
}