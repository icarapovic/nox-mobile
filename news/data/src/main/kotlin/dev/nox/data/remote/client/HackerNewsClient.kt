package dev.nox.data.remote.client

import dev.nox.data.remote.dto.HackerNewsCommentDto
import dev.nox.data.remote.dto.HackerNewsStoryDto
import dev.nox.news.domain.exceptions.DataFetchingException
import dev.nox.news.domain.exceptions.NoInternetException
import okhttp3.OkHttpClient
import okio.IOException
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.create

class HackerNewsClient (
    okHttpHttp: OkHttpClient,
    converterFactory: Converter.Factory,
) {

    private val baseUrl = "https://hacker-news.firebaseio.com/v0/"

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpHttp)
        .addConverterFactory(converterFactory)
        .build()
        .create<HackerNewsApi>()

    suspend fun fetchStory(storyId: Long): Result<HackerNewsStoryDto> {
        try {
            val story = retrofitClient.fetchStory(storyId)
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(DataFetchingException("HTTP ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            return Result.failure(NoInternetException(e.message))
        }
    }

    suspend fun fetchTopStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchTopStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(DataFetchingException("HTTP ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            return Result.failure(NoInternetException(e.message))
        }
    }

    suspend fun fetchBestStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchBestStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(DataFetchingException("HTTP ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            return Result.failure(NoInternetException(e.message))
        }
    }

    suspend fun fetchNewStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchNewStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(DataFetchingException("HTTP ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            return Result.failure(NoInternetException(e.message))
        }
    }

    suspend fun fetchAskStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchAskStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(DataFetchingException("HTTP ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            return Result.failure(NoInternetException(e.message))
        }
    }

    suspend fun fetchShowStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchShowStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(DataFetchingException("HTTP ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            return Result.failure(NoInternetException(e.message))
        }
    }

    suspend fun fetchJobStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchJobStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(DataFetchingException("HTTP ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            return Result.failure(NoInternetException(e.message))
        }
    }

    suspend fun fetchComment(commentId: Long): Result<HackerNewsCommentDto?> {
        try {
            val story = retrofitClient.fetchComment(commentId)
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(DataFetchingException("HTTP ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            return Result.failure(NoInternetException(e.message))
        }
    }
}