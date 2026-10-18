package dev.nox.data.remote.dto

import dev.nox.data.local.entity.HackerNewsCommentEntity
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
) {

    /**
     * Flattens the [children] tree in pre-order, so every comment
     * is directly followed by its replies. Siblings are sorted newest first
     * */
    fun toCommentEntities(): List<HackerNewsCommentEntity> = buildList { addComments(children, depth = 0) }

    private fun MutableList<HackerNewsCommentEntity>.addComments(items: List<AlgoliaItemDto>, depth: Int) {
        items.sortedByDescending { it.time }.forEach { item ->
            add(item.toCommentEntity(storyId = this@AlgoliaItemDto.id, position = size, depth = depth))
            addComments(item.children, depth + 1)
        }
    }

    private fun toCommentEntity(storyId: Long, position: Int, depth: Int) = HackerNewsCommentEntity(
        id = id,
        storyId = storyId,
        position = position,
        depth = depth,
        parent = parentId,
        text = text,
        author = author,
        timestamp = time
    )
}
