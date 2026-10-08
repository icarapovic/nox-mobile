package dev.nox.news.data.remote.hn.dto

import dev.nox.news.data.local.hn.HackerNewsEntity
import kotlinx.serialization.Serializable

@Serializable
data class HackerNewsStoryDto(
    val id: Long,
    val title: String,
    val text: String? = null,
    val by: String,
    val time: Long,
    val url: String? = null,
    val dead: Boolean? = false,
    val type: String,
    val kids: List<Long> = listOf(),
    val score: Int? = null,
    val descendants: Int? = null
)

fun HackerNewsStoryDto.toEntity(category: String, rank: Int) = HackerNewsEntity(
    id = id,
    title = title,
    text = text,
    author = by,
    timestamp = time,
    url = url,
    storyType = type,
    comments = kids.joinToString(";"),
    category = category,
    rank = rank,
    score = score,
    descendants = descendants
)