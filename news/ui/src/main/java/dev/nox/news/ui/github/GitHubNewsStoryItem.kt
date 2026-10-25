package dev.nox.news.ui.github

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nox.news.domain.models.github.GitHubRepository
import dev.nox.news.ui.components.HorizontalDivider
import dev.nox.news.ui.nav.LocalNavBackStack
import dev.nox.news.ui.theme.PlexMono
import dev.nox.news.ui.theme.PlexSans
import java.util.Locale

@Composable
fun GitHubRepositoryItem(repo: GitHubRepository) {
    val backStack = LocalNavBackStack.current

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .clickable(onClick = {
                //backStack.add(DetailsPageNavKey(newsSource, story.id))
            })
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        ) {
            Text(
                color = Color.White,
                letterSpacing = 0.sp,
                fontFamily = PlexSans,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                text = repo.fullName
            )

            repo.description?.let { desc ->
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = Color.Gray,
                    letterSpacing = 0.sp,
                    fontFamily = PlexSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = desc
                )
            }

            Text(
                modifier = Modifier.padding(top = 6.dp, bottom = 12.dp),
                fontWeight = FontWeight.Normal,
                fontFamily = PlexMono,
                fontSize = 12.sp,
                lineHeight = 10.sp,
                color = Color.Gray,
                text = "★ ${repo.stars.formatStars()} · ${repo.language ?: "Plaintext"}"
            )
        }

        HorizontalDivider()
    }

}

fun Int.formatStars(): String {
    if (this >= 100_000) {
        return String.format(Locale.getDefault(), "%.1fk", this / 100_000f)
    }

    if (this >= 10_000) {
        return String.format(Locale.getDefault(), "%.1fk", this / 10_000f)
    }

    if (this >= 1_000) {
        return String.format(Locale.getDefault(), "%.1fk", this / 1_000f)
    }

    return this.toString()
}
