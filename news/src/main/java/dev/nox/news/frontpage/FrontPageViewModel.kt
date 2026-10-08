package dev.nox.news.frontpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.nox.logging.logger.Logger
import dev.nox.news.domain.models.HackerNewsItem
import dev.nox.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import java.time.Instant

class FrontPageViewModel(
    private val newsRepository: NewsRepository,
    private val logger: Logger,
) : ViewModel(), KoinComponent {
    private val _uiState = MutableStateFlow(FrontPageUiState())
    val uiState: StateFlow<FrontPageUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            newsRepository.observeHackerNewsTopStories().collect { stories ->
                logger.info { "Stories refreshed: count: ${stories.count()}" }
                _uiState.tryEmit(FrontPageUiState(stories))
            }
        }

        viewModelScope.launch {
            newsRepository.fetchHackerNewsTopStories(50)
        }
    }

}