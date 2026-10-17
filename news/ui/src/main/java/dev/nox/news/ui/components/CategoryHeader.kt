package dev.nox.news.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import dev.nox.news.ui.theme.PlexMono
import dev.nox.news.ui.theme.PlexSans

@Composable
fun CategoryHeader(
    color: Color,
    title: String
) {
    Row (
        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier =
            Modifier
                .padding(start = 16.dp, end = 12.dp)
                .size(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
        Text(
            text = title,
            color = Color.White,
            fontFamily = PlexSans,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

