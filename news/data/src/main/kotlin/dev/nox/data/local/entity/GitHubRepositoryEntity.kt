package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "gh_repository")
data class GitHubRepositoryEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "language")
    val language: String?,
    @ColumnInfo(name = "owner_id")
    val ownerId: Long,
    @ColumnInfo(name = "owner_login")
    val ownerLogin: String,
    @ColumnInfo(name = "owner_avatar_url")
    val ownerAvatarUrl: String?
)
