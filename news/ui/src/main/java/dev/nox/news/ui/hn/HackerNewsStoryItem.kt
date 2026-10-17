package dev.nox.news.ui.hn

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import java.time.Duration
import java.time.Instant

@Composable
fun HackerNewsStoryItem(index: Int, story: HackerNewsItem, now: Instant) {
    val context = LocalContext.current
    val age = Duration.between(Instant.ofEpochSecond(story.timestamp), now).formatAge()

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .clickable(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(story.url))
                context.startActivity(intent)
            })
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 32.dp, top = 8.dp)
        ) {
            Text(
                text = "${index + 1}",
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    color = Color.White,
                    letterSpacing = 0.sp,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    text = story.title
                )

                Text(
                    modifier = Modifier.padding(top = 6.dp, bottom = 12.dp),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 10.sp,
                    color = Color.Gray,
                    text = "${story.score} pts · ${story.comments.size} comments · $age ago"
                )

            }
        }

        Box(
            modifier = Modifier.background(Color(0x33FFFFFF)).fillMaxWidth().height(0.25.dp)
        )
    }

}
private fun Duration.formatAge(): String =
    if (toHours() < 1) "${toMinutes().coerceAtLeast(0)}m" else "${toHours()}h"
