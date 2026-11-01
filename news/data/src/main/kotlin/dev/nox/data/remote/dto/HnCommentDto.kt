package dev.nox.data.remote.dto

import dev.nox.data.local.entity.HnCommentEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HnCommentDto(
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
    val children: List<HnCommentDto> = listOf()
) {

    /**
     * Flattens the [children] tree in pre-order, so every comment
     * is directly followed by its replies. Siblings are sorted newest first
     * */
    fun toCommentEntities(): List<HnCommentEntity> = buildList { addComments(children, depth = 0) }

    private fun MutableList<HnCommentEntity>.addComments(items: List<HnCommentDto>, depth: Int) {
        items.sortedByDescending { it.time }.forEach { item ->
            add(item.toCommentEntity(storyId = this@HnCommentDto.id, position = size, depth = depth))
            addComments(item.children, depth + 1)
        }
    }

    private fun toCommentEntity(storyId: Long, position: Int, depth: Int) = HnCommentEntity(
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
