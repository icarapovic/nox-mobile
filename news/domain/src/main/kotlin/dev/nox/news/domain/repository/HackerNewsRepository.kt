package dev.nox.news.domain.repository

import dev.nox.news.domain.models.Source
import dev.nox.news.domain.models.hackernews.HnComment
import dev.nox.news.domain.models.hackernews.HnStory
import kotlinx.coroutines.flow.Flow

interface HackerNewsRepository {

    fun refreshData(source: Source): Flow<Float>
    fun observeTopStories(): Flow<List<HnStory>>
    fun observeBestStories(): Flow<List<HnStory>>
    fun observeNewStories(): Flow<List<HnStory>>
    fun observeShowStories(): Flow<List<HnStory>>
    fun observeAskStories(): Flow<List<HnStory>>
    fun observeJobsStories(): Flow<List<HnStory>>
    fun getStory(id: Long): Flow<HnStory?>
    fun getStoryComments(id: Long): Flow<List<HnComment>>
}