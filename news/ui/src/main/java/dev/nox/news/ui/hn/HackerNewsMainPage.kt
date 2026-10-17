package dev.nox.news.ui.hn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import dev.nox.news.domain.repository.NewsSource
import dev.nox.news.ui.components.CategoryHeader
import dev.nox.news.ui.components.SourceTab
import dev.nox.news.ui.home.HomeViewModel
import dev.nox.news.ui.nav.CategoryMorePageNavKey
import dev.nox.news.ui.nav.LocalNavBackStack
import dev.nox.news.ui.theme.PlexSans
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HackerNewsPage(
    viewModel: HomeViewModel = koinViewModel()
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        HackerNewsCategory(
            source = NewsSource.HN_TOP,
            categoryTitle = "Top",
            moreButtonText = "See more Top stories",
            newsFlow =  viewModel.hnTopStories,
        )

        HackerNewsCategory(
            source = NewsSource.HN_NEW,
            categoryTitle = "New",
            moreButtonText = "See more New stories",
            newsFlow =  viewModel.hnNewStories,
        )

        HackerNewsCategory(
            source = NewsSource.HN_BEST,
            categoryTitle = "Best",
            moreButtonText = "See more Best stories",
            newsFlow =  viewModel.hnBestStories,
        )

        HackerNewsCategory(
            source = NewsSource.HN_ASK,
            categoryTitle = "Ask",
            moreButtonText = "See more questions",
            newsFlow =  viewModel.hnAskStories,
        )

        HackerNewsCategory(
            source = NewsSource.HN_SHOW,
            categoryTitle = "Show",
            moreButtonText = "See more works",
            newsFlow =  viewModel.hnShowStories,
        )

        HackerNewsCategory(
            source = NewsSource.HN_JOB,
            categoryTitle = "Jobs",
            moreButtonText = "See more jobs",
            newsFlow =  viewModel.hnJobsStories,
        )
    }
}

@Composable
fun HackerNewsCategory(
    source: NewsSource,
    categoryTitle: String,
    moreButtonText: String,
    newsFlow: StateFlow<List<NewsItem>>,
) {
    val backStack = LocalNavBackStack.current
    val newsList = newsFlow.collectAsStateWithLifecycle().value
    var now by remember { mutableStateOf(Instant.now()) }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { now = Instant.now() }

    Column {
        CategoryHeader(SourceTab.HACKER_NEWS.color, categoryTitle)
        newsList.take(5).forEachIndexed { index, item ->
            HackerNewsStoryItem(index, item as HackerNewsItem, now)
        }
        SeeMoreButton(
            title = moreButtonText,
            onSeeMoreClicked = {
                backStack.add(CategoryMorePageNavKey(source))
            }
        )
    }
}

@Composable
fun SeeMoreButton(
    title: String,
    onSeeMoreClicked: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
        onClick = onSeeMoreClicked,
        content = {
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontFamily = PlexSans,
                    fontSize = 14.sp
                )
                Icon(
                    modifier = Modifier.padding(start = 12.dp).size(16.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    tint = Color.White,
                    contentDescription = "Forward icon"
                )
            }
        }
    )
}

