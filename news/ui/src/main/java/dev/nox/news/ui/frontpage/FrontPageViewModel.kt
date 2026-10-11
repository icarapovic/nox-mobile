package dev.nox.news.ui.frontpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.nox.logging.logger.Logger
import dev.nox.news.domain.models.hackernews.HackerNewsItem
import dev.nox.news.domain.repository.NewsRepository
import dev.nox.news.domain.repository.NewsSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent

class FrontPageViewModel(
    newsRepository: NewsRepository,
    private val logger: Logger,
) : ViewModel(), KoinComponent {

    val uiState: StateFlow<HackerNewsTopStories> = combine(
        newsRepository.observeNews(NewsSource.HN_TOP),
        newsRepository.refreshNews(NewsSource.HN_TOP, 50)
    ) { stories, progress ->
        HackerNewsTopStories(
            stories = stories.map { it as HackerNewsItem },
            progress = progress
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = HackerNewsTopStories()
    )

}