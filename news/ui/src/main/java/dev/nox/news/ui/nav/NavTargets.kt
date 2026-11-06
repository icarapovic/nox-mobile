package dev.nox.news.ui.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenNavKey : NavKey

@Serializable
data class HnStoryPageNavKey(
    val storyId: Long,
    val origin: StoryOrigin
): NavKey

enum class StoryOrigin { FEED, BOOKMARKS }

@Serializable
object BookmarksNavKey: NavKey