package dev.nox.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlgoliaItemDto(
    val id: Long,
    @SerialName("created_at_i") val time: Long,
    val type: String,
    val author: String? = null,
    val title: String? = null,
    val url: String? = null,
    val text: String? = null,
    val points: Int? = null,
    @SerialName("parent_id") val parentId: Long? = null,
    @SerialName("story_id") val storyId: Long? = null,
    val children: List<AlgoliaItemDto> = listOf()
)
