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
import dev.nox.news.ui.components.CategoryHeader
import dev.nox.news.ui.components.SourceTab
import dev.nox.news.ui.home.HomeViewModel
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HackerNewsPage(
    viewModel: HomeViewModel = koinViewModel()
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        HackerNewsCategory(
            categoryTitle = "Top stories",
            moreButtonText = "See more Top stories",
            newsFlow =  viewModel.hnTopStories,
            onSeeMoreClicked = {}
        )

        HackerNewsCategory(
            categoryTitle = "New stories",
            moreButtonText = "See more New stories",
            newsFlow =  viewModel.hnNewStories,
            onSeeMoreClicked = {}
        )

        HackerNewsCategory(
            categoryTitle = "Best stories",
            moreButtonText = "See more Best stories",
            newsFlow =  viewModel.hnBestStories,
            onSeeMoreClicked = {}
        )
    }
}

@Composable
fun HackerNewsCategory(
    categoryTitle: String,
    moreButtonText: String,
    newsFlow: StateFlow<List<NewsItem>>,
    onSeeMoreClicked: () -> Unit
) {
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
            onSeeMoreClicked = onSeeMoreClicked
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

