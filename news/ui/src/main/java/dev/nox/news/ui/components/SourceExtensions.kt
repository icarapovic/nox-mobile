package dev.nox.news.ui.components

import androidx.compose.ui.graphics.Color
import dev.nox.news.domain.repository.NewsSource

fun NewsSource.color(): Color {
    return when(this) {
        NewsSource.HN_TOP,
        NewsSource.HN_BEST,
        NewsSource.HN_NEW,
        NewsSource.HN_ASK,
        NewsSource.HN_JOB,
        NewsSource.HN_SHOW -> Color(0xFFEA7E15)
    }
}

fun NewsSource.sourceTitle(): String {
    return when(this) {
        NewsSource.HN_TOP,
        NewsSource.HN_BEST,
        NewsSource.HN_NEW,
        NewsSource.HN_ASK,
        NewsSource.HN_JOB,
        NewsSource.HN_SHOW -> "Hacker News"
    }
}

fun NewsSource.categoryTitle(): String {
    return when(this) {
        NewsSource.HN_TOP -> "Top Stories"
        NewsSource.HN_BEST -> "Best Stories"
        NewsSource.HN_NEW -> "New Stories"
        NewsSource.HN_ASK -> "HN Ask"
        NewsSource.HN_JOB -> "HN Job"
        NewsSource.HN_SHOW -> "HN Show"
    }
}