package dev.nox.news.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.models.hackernews.HackerNewsItem

@Composable
fun DetailPageToolbar(
    story: HackerNewsItem,
    onBookmarkClicked: (NewsItem) -> Unit = {},
) {
    Column {
        Row (
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavigateBackIcon()
            Spacer(modifier = Modifier.weight(1f))
            BookmarkIcon { onBookmarkClicked(story) }
            story.url?.let { url ->
                ShareIcon(url)
            }
        }
    }
}