package dev.nox.news.ui.hn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import dev.nox.news.domain.repository.NewsRepository
import dev.nox.news.domain.repository.NewsSource
import dev.nox.news.ui.components.DetailPageComments
import dev.nox.news.ui.components.DetailPageToolbar
import dev.nox.news.ui.home.HomeViewModel
import dev.nox.news.ui.pages.HnDetailsPageStory
import org.koin.compose.koinInject
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

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(Color.Black)
    ) {
        story?.let {
            DetailPageToolbar(story)
            HnDetailsPageStory(story)
        }
        DetailPageComments(source, storyId)
    }
}