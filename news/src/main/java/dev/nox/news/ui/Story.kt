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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nox.news.domain.models.hackernews.HackerNewsItem

@Composable
fun Story(story: HackerNewsItem) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.padding(horizontal = 8.dp).clickable(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(story.url))
            context.startActivity(intent)
        }),
    ) {
        Column(
            modifier = Modifier.padding(start = 8.dp)
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