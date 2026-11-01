package dev.nox.news.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.domain.models.Source
import dev.nox.news.ui.hn.HnStoryListItem
import dev.nox.news.ui.home.HomeViewModel
import java.time.Instant
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Page(
    source: Source,
    viewModel: HomeViewModel = koinViewModel()
) {
    val storyCount = viewModel.categoryStoryCount
    val newsList = when(source) {
        Source.HN_TOP -> viewModel.hnTopStories.collectAsStateWithLifecycle().value
        Source.HN_NEW -> viewModel.hnNewStories.collectAsStateWithLifecycle().value
        Source.HN_BEST -> viewModel.hnBestStories.collectAsStateWithLifecycle().value
        Source.HN_ASK -> viewModel.hnAskStories.collectAsStateWithLifecycle().value
        Source.HN_SHOW -> viewModel.hnShowStories.collectAsStateWithLifecycle().value
        Source.HN_JOBS -> viewModel.hnJobsStories.collectAsStateWithLifecycle().value
    }

    var now by remember { mutableStateOf(Instant.now()) }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { now = Instant.now() }

    Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(top = 8.dp)) {
        newsList.take(storyCount).forEachIndexed { index, item ->
            when(source) {
                Source.HN_TOP,
                Source.HN_NEW,
                Source.HN_BEST,
                Source.HN_ASK,
                Source.HN_SHOW,
                Source.HN_JOBS -> HnStoryListItem(index, item, now)
            }
        }
    }
}

