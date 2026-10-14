package dev.nox.news.ui.hn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import dev.nox.news.ui.Story
import dev.nox.news.ui.components.CategoryHeader
import dev.nox.news.ui.components.SourceTab
import dev.nox.news.ui.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.collections.get

@Composable
fun HackerNewsPage(
    viewModel: HomeViewModel = koinViewModel()
) {

    val news = viewModel.newsState.collectAsStateWithLifecycle().value

    Column {
        CategoryHeader(SourceTab.HACKER_NEWS.color, "Top stories")

        LazyColumn {
            items(
                count = news.size,
                key = { index ->
                    val story = news[index] as HackerNewsItem
                    story.title + story.url
                }
            ) { index -> Story(index, news[index] as HackerNewsItem) }
        }
    }

}