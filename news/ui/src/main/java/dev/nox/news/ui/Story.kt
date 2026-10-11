package dev.nox.news.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.nox.news.domain.models.hackernews.HackerNewsItem

@Composable
fun Story(story: HackerNewsItem) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp).clickable(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(story.url))
            context.startActivity(intent)
        }),
    ) {
        AsyncImage(
            model = faviconUrl(story.url),
            contentDescription = null,
            modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
            error = painterResource(R.drawable.hn)
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                text = story.title
            )
            story.url?.let { url ->
                Text(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    text = "(${url
                        .replace("https://", "")
                        .replace("www.", "")
                        .substringBefore("/")
                    })",
                )
            }
            Spacer(modifier = Modifier.background(Color.White).height(4.dp))
        }
    }
}

fun faviconUrl(pageUrl: String?, size: Int = 64): String? {
    val host = runCatching { java.net.URI(pageUrl).host }.getOrNull() ?: return null
    return "https://www.google.com/s2/favicons?domain=$host&sz=$size"
}