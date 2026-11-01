package dev.nox.news.ui.hn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.nox.news.domain.models.hackernews.HnComment
import dev.nox.news.domain.models.hackernews.HnStory
import dev.nox.news.domain.repository.HackerNewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HnStoryPageViewModel(
   storyId: Long,
   hnRepo: HackerNewsRepository
) : ViewModel() {

    val story: StateFlow<HnStory?> = hnRepo.getStory(storyId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    val comments: StateFlow<List<HnComment>> = hnRepo.getStoryComments(storyId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )
}