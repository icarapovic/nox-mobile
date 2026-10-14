package dev.nox.news.ui.components

import androidx.compose.ui.graphics.Color

enum class SourceTab(
    val title: String,
    val color: Color
) {
    HACKER_NEWS("Hacker News", Color(0xFFEA7E15)),
    GITHUB("GitHub", Color(0xFF79449B)),
    GITLAB("GitLab", Color(0xFFCA0000)),
    CODEBERG("Codeberg", Color(0xFF16AFF1)),
    ARS_TECHNICA("ArsTechnica", Color(0xFFFF5722))
}