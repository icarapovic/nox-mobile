package dev.nox.news.ui.frontpage

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import dev.nox.news.ui.Story
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HackerNews(viewModel: FrontPageViewModel = koinViewModel()) {
    val news = viewModel.newsState.collectAsStateWithLifecycle().value

    LazyColumn {
        items(
            count = news.size,
            key = { index ->
                val story = news[index] as HackerNewsItem
                story.title + story.url
            }
        ) { index -> Story(news[index] as HackerNewsItem) }
    }
}
