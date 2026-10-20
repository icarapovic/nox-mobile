package dev.nox.data.remote.client

import dev.nox.data.remote.dto.GitHubSearchResponseDto
import dev.nox.news.domain.exceptions.DataFetchingException
import dev.nox.news.domain.exceptions.NoInternetException
import okhttp3.OkHttpClient
import okio.IOException
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.create

class GitHubClient(
    okHttpHttp: OkHttpClient,
    converterFactory: Converter.Factory,
) {

    enum class Sort(val value: String) {
        STARS("stars"),
        FORKS("forks"),
        UPDATED("updated")
    }

    private val baseUrl = "https://api.github.com/"

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpHttp)
        .addConverterFactory(converterFactory)
        .build()
        .create<GitHubApi>()

    /**
     * @param language optional programming language filter, appended to the query as `language:<language>`
     * @param sort `null` sorts by best match
     */
    suspend fun searchRepositories(
        query: String,
        language: String? = null,
        sort: Sort? = null,
        descending: Boolean = true,
        perPage: Int = 30,
        page: Int = 1
    ): Result<GitHubSearchResponseDto> {
        val q = listOfNotNull(
            query.trim().takeIf { it.isNotEmpty() },
            language?.trim()?.takeIf { it.isNotEmpty() }?.let { "language:$it" }
        ).joinToString(" ")
        try {
            val response = retrofitClient.searchRepositories(
                query = q,
                sort = sort?.value,
                order = if (descending) "desc" else "asc",
                perPage = perPage,
                page = page
            )
            return Result.success(response)
        } catch (e: HttpException) {
            return Result.failure(DataFetchingException("HTTP ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            return Result.failure(NoInternetException(e.message))
        }
    }
}
