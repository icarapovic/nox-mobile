package dev.nox.news.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun Story(index: Int, story: HackerNewsItem) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clickable(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(story.url))
                    context.startActivity(intent)
                }),
        ) {
            Text(
                text = "${index + 1}",
                color = Color.Gray,
                fontSize = 12.sp
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    color = Color.White,
                    letterSpacing = 0.05.sp,
                    fontSize = 15.sp,
                    text = story.title
                )

                Text(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 10.sp,
                    color = Color.Gray,
                    text = "${story.score} pts"
                )

            }
        }
        //Box(Modifier.height(1.dp).background(Color.DarkGray).fillMaxWidth())
    }

}