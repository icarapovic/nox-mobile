package dev.nox.news.ui.nav

import androidx.navigation3.runtime.NavKey
import dev.nox.news.domain.repository.NewsSource
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenNavKey : NavKey

@Serializable
data class MoreStoriesNavKey(
    val newsSource: NewsSource
): NavKey

@Serializable
data class DetailsPageNavKey(
    val newsSource: NewsSource,
    val storyId: Long
): NavKey