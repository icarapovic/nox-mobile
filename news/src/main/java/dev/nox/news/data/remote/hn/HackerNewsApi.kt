package dev.nox.news.data.remote.hn

import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsApi {

    @GET("item/{storyId}.json")
    suspend fun fetchStory(@Path("storyId") storyId: Long): HackerNewsStory

    @GET("topstories.json")
    suspend fun fetchTopStories(): List<Long>

    @GET("beststories.json")
    suspend fun fetchBestStories(): List<Long>

    @GET("newstories.json")
    suspend fun fetchNewStories(): List<Long>

    @GET("askstories.json")
    suspend fun fetchAskStories(): List<Long>

    @GET("showstories.json")
    suspend fun fetchShowStories(): List<Long>

    @GET("jobstories.json")
    suspend fun fetchJobStories(): List<Long>

    @GET("item/{commentId}.json")
    suspend fun fetchComment(@Path("commentId") commentId: Long): HackerNewsComment
}