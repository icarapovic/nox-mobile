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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nox.news.domain.models.hackernews.HackerNewsItem

@Composable
fun HackerNewsStoryItem(index: Int, story: HackerNewsItem) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(start = 12.dp, top = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(story.url))
                    context.startActivity(intent)
                }),
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
                    text = "${story.score} pts · X comments · Xh"
                )

            }
        }

        Box(
            modifier = Modifier.background(Color(0x33FFFFFF)).fillMaxWidth().height(0.25.dp)
        )
    }

}