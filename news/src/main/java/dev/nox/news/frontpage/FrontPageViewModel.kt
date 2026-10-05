package dev.nox.news.frontpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.nox.news.data.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class FrontPageViewModel(
    private val newsRepository: NewsRepository
) : ViewModel(), KoinComponent {
    private val _uiState = MutableStateFlow(FrontPageUiState())
    val uiState: StateFlow<FrontPageUiState> = _uiState.asStateFlow()

    init {
        loadTopStories()
    }

    fun loadTopStories() {
        viewModelScope.launch {
            val stories = newsRepository.getNews()
            _uiState.tryEmit(FrontPageUiState(stories))
        }
    }
}