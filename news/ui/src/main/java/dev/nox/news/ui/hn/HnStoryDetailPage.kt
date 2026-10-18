package dev.nox.news.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import dev.nox.news.ui.components.ButtonOpenInBrowser
import dev.nox.news.ui.components.ColoredDotHeader
import dev.nox.news.ui.components.categoryTitle
import dev.nox.news.ui.components.color
import dev.nox.news.ui.components.sourceTitle
import dev.nox.news.ui.formatAge
import dev.nox.news.ui.theme.PlexMono
import dev.nox.news.ui.theme.PlexSans
import java.time.Duration
import java.time.Instant

@Composable
fun HnStoryDetailPage(hnStory: NewsItem) {

    val story = hnStory as HackerNewsItem

    var now by remember { mutableStateOf(Instant.now()) }
    val age = Duration.between(Instant.ofEpochSecond(story.timestamp), now).formatAge()

    Column {
        ColoredDotHeader(
            color = story.newsSource.color(),
            title = "${story.newsSource.sourceTitle()} · ${story.newsSource.categoryTitle()}",
            smallHeader = true
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            text = story.title,
            fontFamily = PlexSans,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        story.url?.let { url ->
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = url,
                fontFamily = PlexMono,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "${story.score} pts · ${story.author} · $age",
            fontFamily = PlexMono,
            fontSize = 14.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        story.description?.let { desc ->
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                text = AnnotatedString.fromHtml(desc),
                fontFamily = PlexSans,
                fontSize = 16.sp,
                color = Color.White,
            )
        }

        story.url?.let { url ->
            ButtonOpenInBrowser(url)
        }

    }

}

