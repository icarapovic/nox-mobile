package dev.nox.news.frontpage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.nox.news.data.remote.hn.HackerNewsClient
import dev.nox.news.data.remote.hn.HackerNewsStory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class FrontPageViewModel(
    private val hnClient: HackerNewsClient
) : ViewModel(), KoinComponent {
    private val _uiState = MutableStateFlow(FrontPageUiState())
    val uiState: StateFlow<FrontPageUiState> = _uiState.asStateFlow()

    init {
        loadTopStories()
    }

    fun loadTopStories() {
        viewModelScope.launch {
            hnClient.fetchTopStories().fold(
                onSuccess = {
                    val stories = mutableListOf<HackerNewsStory>()
                    it.take(10).forEach { storyId ->
                        hnClient.fetchStory(storyId).fold(
                            onSuccess = { story ->
                                stories.add(story)
                            },
                            onFailure = { error ->
                                Log.d("HN", "Failed to fetch story $storyId: ${error.message}")
                            }
                        )
                    }
                    _uiState.value = FrontPageUiState(stories, true)
                },
                onFailure = { error ->
                    _uiState.value = FrontPageUiState(emptyList(), true, error.message ?: "Unknown error")
                }
            )
        }
    }
}