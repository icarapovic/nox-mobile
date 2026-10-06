package dev.nox.news.data.remote.hn.client

import dev.nox.net.BaseUrlProvider
import dev.nox.news.data.remote.hn.dto.HackerNewsCommentDto
import dev.nox.news.data.remote.hn.dto.HackerNewsStoryDto
import dev.nox.news.domain.ContentNotAccessibleException
import okhttp3.OkHttpClient
import okio.IOException
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.create

class HackerNewsClient(
    baseUrlProvider: BaseUrlProvider,
    okHttpHttp: OkHttpClient,
    converterFactory: Converter.Factory
) {

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(baseUrlProvider.provideBaseUrl())
        .client(okHttpHttp)
        .addConverterFactory(converterFactory)
        .build()
        .create<HackerNewsApi>()

    suspend fun fetchStory(storyId: Long): Result<HackerNewsStoryDto> {
        try {
            val story = retrofitClient.fetchStory(storyId)
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(ContentNotAccessibleException(e))
        } catch (e: IOException) {
            return Result.failure(ContentNotAccessibleException(e))
        }
    }

    suspend fun fetchTopStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchTopStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(ContentNotAccessibleException(e))
        } catch (e: IOException) {
            return Result.failure(ContentNotAccessibleException(e))
        }
    }

    suspend fun fetchBestStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchBestStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(ContentNotAccessibleException(e))
        } catch (e: IOException) {
            return Result.failure(ContentNotAccessibleException(e))
        }
    }

    suspend fun fetchNewStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchNewStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(ContentNotAccessibleException(e))
        } catch (e: IOException) {
            return Result.failure(ContentNotAccessibleException(e))
        }
    }

    suspend fun fetchAskStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchAskStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(ContentNotAccessibleException(e))
        } catch (e: IOException) {
            return Result.failure(ContentNotAccessibleException(e))
        }
    }

    suspend fun fetchShowStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchShowStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(ContentNotAccessibleException(e))
        } catch (e: IOException) {
            return Result.failure(ContentNotAccessibleException(e))
        }
    }

    suspend fun fetchJobStories(): Result<List<Long>> {
        try {
            val story = retrofitClient.fetchJobStories()
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(ContentNotAccessibleException(e))
        } catch (e: IOException) {
            return Result.failure(ContentNotAccessibleException(e))
        }
    }

    suspend fun fetchComment(commentId: Long): Result<HackerNewsCommentDto?> {
        try {
            val story = retrofitClient.fetchComment(commentId)
            return Result.success(story)
        } catch (e: HttpException) {
            return Result.failure(ContentNotAccessibleException(e))
        } catch (e: IOException) {
            return Result.failure(ContentNotAccessibleException(e))
        }
    }
}