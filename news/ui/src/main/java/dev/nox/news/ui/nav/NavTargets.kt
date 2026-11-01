package dev.nox.news.ui.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenNavKey : NavKey

@Serializable
data class HnStoryPageNavKey(
    val storyId: Long
): NavKey

@Serializable
object BookmarksNavKey: NavKey