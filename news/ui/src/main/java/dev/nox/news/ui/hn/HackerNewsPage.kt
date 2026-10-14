package dev.nox.news.ui.hn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import dev.nox.news.ui.components.CategoryHeader
import dev.nox.news.ui.components.SourceTab
import dev.nox.news.ui.home.HomeViewModel
import kotlinx.coroutines.flow.StateFlow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HackerNewsPage(
    viewModel: HomeViewModel = koinViewModel()
) {
    HackerNewsCategory(
        categoryTitle = "Top stories",
        newsFlow =  viewModel.newsState,
        onSeeMoreClicked = {}
    )
}

@Composable
fun HackerNewsCategory(
    categoryTitle: String,
    newsFlow: StateFlow<List<NewsItem>>,
    onSeeMoreClicked: () -> Unit
) {
    val newsList = newsFlow.collectAsStateWithLifecycle().value

    Column {
        CategoryHeader(SourceTab.HACKER_NEWS.color, categoryTitle)
        LazyColumn {
            items(count = newsList.take(5).count()) { index ->
                HackerNewsStoryItem(index, newsList[index] as HackerNewsItem)
            }
        }
        SeeMoreButton(
            title = "See all Top stories",
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

