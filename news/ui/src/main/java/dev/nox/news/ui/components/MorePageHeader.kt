package dev.nox.news.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nox.news.domain.repository.NewsSource
import dev.nox.news.ui.theme.PlexMono
import dev.nox.news.ui.theme.PlexSans

@Composable
fun MorePageHeader(
    newsSource: NewsSource,
) {

    Column(
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        Row (
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigateBackIcon()
            Box(modifier =
                Modifier
                    .padding(start = 16.dp, end = 12.dp)
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(newsSource.color())
            )
            Text(
                text = newsSource.sourceTitle(),
                color = Color.Gray,
                fontFamily = PlexMono,
                fontSize = 16.sp,
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            text = newsSource.categoryTitle(),
            color = Color.White,
            fontFamily = PlexSans,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier.background(Color(0x33FFFFFF)).fillMaxWidth().height(0.25.dp)
        )
    }
}