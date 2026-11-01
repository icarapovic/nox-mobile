package dev.nox.news.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.nox.news.ui.theme.PlexMono
import dev.nox.news.ui.theme.PlexSans

@Composable
fun TextNormal(text: String, modifier: Modifier = Modifier, textColor: Color = Color.White) {
    Text(
        modifier = modifier,
        fontFamily = PlexSans,
        color = textColor,
        fontSize = 16.sp,
        text = text
    )
}

@Composable
fun TextSmall(text: String, modifier: Modifier = Modifier, textColor: Color = Color.White) {
    Text(
        modifier = modifier,
        fontFamily = PlexMono,
        color = textColor,
        fontSize = 12.sp,
        text = text
    )
}

@Composable
fun TextLarge(text: String, modifier: Modifier = Modifier, textColor: Color = Color.White) {
    Text(
        modifier = modifier,
        fontFamily = PlexSans,
        color = textColor,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        text = text
    )
}