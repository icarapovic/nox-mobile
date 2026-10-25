package dev.nox.data.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import dev.nox.data.local.entity.GitHubRepositoryEntity

@Dao
interface GitHubDao {

    @Query("SELECT * FROM gh_repository")
    suspend fun getRepositories(): List<GitHubRepositoryEntity>

    @Upsert
    suspend fun upsertRepositories(repositories: List<GitHubRepositoryEntity>)
}
