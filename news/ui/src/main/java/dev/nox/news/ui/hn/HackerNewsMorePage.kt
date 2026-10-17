package dev.nox.news.ui.hn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import dev.nox.news.domain.repository.NewsSource
import dev.nox.news.ui.components.MorePageHeader
import dev.nox.news.ui.components.NavigateBackIcon
import dev.nox.news.ui.home.HomeViewModel
import dev.nox.news.ui.nav.LocalNavBackStack
import org.koin.compose.viewmodel.koinViewModel
import java.time.Instant

@Composable
fun HackerNewsMorePage(
    newsSource: NewsSource,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val newsList = when(newsSource) {
        NewsSource.HN_TOP -> viewModel.hnTopStories.collectAsStateWithLifecycle()
        NewsSource.HN_BEST -> viewModel.hnBestStories.collectAsStateWithLifecycle()
        NewsSource.HN_NEW -> viewModel.hnNewStories.collectAsStateWithLifecycle()
        NewsSource.HN_ASK -> viewModel.hnAskStories.collectAsStateWithLifecycle()
        NewsSource.HN_JOB -> viewModel.hnJobsStories.collectAsStateWithLifecycle()
        NewsSource.HN_SHOW -> viewModel.hnShowStories.collectAsStateWithLifecycle()
    }.value
    var now by remember { mutableStateOf(Instant.now()) }

    Column {
        MorePageHeader(newsSource)
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            newsList.forEachIndexed { index, item ->
                HackerNewsStoryItem(index, item as HackerNewsItem, now)
            }
        }
    }

}