package dev.nox.news.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.domain.repository.NewsSource
import dev.nox.news.ui.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailPageComments(
    newsSource: NewsSource,
    storyId: Long,
    viewModel: HomeViewModel = koinViewModel()
) {
    viewModel.fetchHackerNewsStory(newsSource, storyId)
        .collectAsStateWithLifecycle(null).value?.let { story ->
            val comments = viewModel.getHackerNewsComments(story.comments)
                .collectAsStateWithLifecycle(listOf()).value

            LazyColumn {
                items(comments.size, key = { index -> comments[index].id}) { index ->
                    comments[index].text?.let { comment ->
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            text = AnnotatedString.fromHtml(comment),
                            color = Color.White
                        )
                    }

                }
            }
    }
}