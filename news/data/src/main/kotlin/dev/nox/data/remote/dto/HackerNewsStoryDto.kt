package dev.nox.data.remote.dto

import dev.nox.data.local.entity.HackerNewsEntity
import dev.nox.news.domain.repository.NewsSource
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
) {
    fun toEntity(newsSource: NewsSource, rank: Int) = HackerNewsEntity(
        id = id,
        newsSource = newsSource.name,
        title = title,
        text = text,
        author = by,
        timestamp = time,
        url = url,
        storyType = type,
        comments = kids.joinToString(";"),
        score = score,
        rank = rank,
        descendants = descendants
    )
}