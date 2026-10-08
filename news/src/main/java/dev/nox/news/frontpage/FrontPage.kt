package dev.nox.news.frontpage

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.ui.Story
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HackerNews(viewModel: FrontPageViewModel = koinViewModel()) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    when (state.value.isSuccessful) {
        true -> {
            LazyColumn {
                items(
                    count = state.value.stories.size,
                    key = { index -> state.value.stories[index].id }
                ) { index ->
                    val story = state.value.stories[index]
                    Story(story)
                }
            }
        }

        false -> {
            Text(state.value.errorMessage)
        }
    }
}
