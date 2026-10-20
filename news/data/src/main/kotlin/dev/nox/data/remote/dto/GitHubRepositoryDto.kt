package dev.nox.data.remote.dto

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
)
