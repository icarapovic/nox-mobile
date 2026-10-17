package dev.nox.news.ui.nav

import androidx.navigation3.runtime.NavKey
import dev.nox.news.domain.repository.NewsSource
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenNavKey : NavKey

@Serializable
data class CategoryMorePageNavKey(
    val newsSource: NewsSource
): NavKey