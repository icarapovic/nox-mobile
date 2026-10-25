package dev.nox.news.domain.models.github

data class GitHubRepository(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val url: String,
    val language: String?,
    val stars: Int,
    val ownerId: Long,
    val ownerLogin: String,
    val ownerAvatarUrl: String?
)
