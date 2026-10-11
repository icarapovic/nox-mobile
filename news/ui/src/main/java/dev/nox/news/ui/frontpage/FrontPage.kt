package dev.nox.news.ui.frontpage

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.ui.Story
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HackerNews(viewModel: FrontPageViewModel = koinViewModel()) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LazyColumn {
        items(
            count = state.stories.size,
            key = { index ->
                val story = state.stories[index]
                story.title + story.url
            }
        ) { index -> Story(state.stories[index]) }
    }
}
