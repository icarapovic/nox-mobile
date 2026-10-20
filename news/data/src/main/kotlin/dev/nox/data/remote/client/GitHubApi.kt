package dev.nox.data.remote.client

import dev.nox.data.remote.dto.GitHubSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1
    ): GitHubSearchResponseDto
}
