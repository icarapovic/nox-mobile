package dev.nox.news.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nox.news.domain.models.HackerNewsItem

@Composable
fun Story(story: HackerNewsItem) {

    Column {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            text = story.title
        )
        story.description?.let {
            Text(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                text = story.description
            )
        } ?: story.url?.let {
            Text(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                text = "(${story.url
                    .replace("https://", "")
                    .replace("www.", "")
                    .substringBefore("/")
                })"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }


}