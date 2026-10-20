package dev.nox.data.remote.dto

import dev.nox.data.local.entity.GitHubRepositoryEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoryDto(
    val id: Long,
    val name: String,
    @SerialName("full_name") val fullName: String,
    val description: String? = null,
    @SerialName("html_url") val url: String,
    val language: String? = null,
    val owner: GitHubOwnerDto
) {

    fun toEntity() = GitHubRepositoryEntity(
        id = id,
        name = name,
        fullName = fullName,
        description = description,
        url = url,
        language = language,
        ownerId = owner.id,
        ownerLogin = owner.login,
        ownerAvatarUrl = owner.avatarUrl
    )
}
