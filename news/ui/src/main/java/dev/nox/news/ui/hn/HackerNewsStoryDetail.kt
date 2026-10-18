package dev.nox.news.ui.hn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.domain.repository.NewsSource
import dev.nox.news.ui.components.DetailPageToolbar
import dev.nox.news.ui.components.detailPageComments
import dev.nox.news.ui.home.HomeViewModel
import dev.nox.news.ui.pages.HnStoryDetailPage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StoryDetailsPage(newsSource: NewsSource, storyId: Long) {
    when(newsSource) {
        NewsSource.HN_TOP,
        NewsSource.HN_BEST,
        NewsSource.HN_NEW,
        NewsSource.HN_ASK,
        NewsSource.HN_JOB,
        NewsSource.HN_SHOW -> HackerNewsStoryDetail(newsSource, storyId)
    }
}

@Composable
fun HackerNewsStoryDetail(
    source: NewsSource,
    storyId: Long,
    viewModel: HomeViewModel = koinViewModel()
) {

    val story = viewModel.fetchHackerNewsStory(source, storyId).collectAsState(initial = null).value
    // remembered so recompositions don't restart the fetch
    val comments = remember(storyId) { viewModel.getHackerNewsComments(storyId) }
        .collectAsStateWithLifecycle(listOf()).value

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(Color.Black)
    ) {
        story?.let { DetailPageToolbar(story) }

        LazyColumn(modifier = Modifier.weight(1f)) {
            story?.let { item(key = "story") { HnStoryDetailPage(story) } }
            detailPageComments(story?.author, comments)
        }
    }
}