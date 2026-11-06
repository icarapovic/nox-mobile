package dev.nox.news.ui.hn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.nox.news.domain.models.hackernews.HnComment
import dev.nox.news.domain.models.hackernews.HnStory
import dev.nox.news.domain.repository.HackerNewsRepository
import dev.nox.news.ui.nav.StoryOrigin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class HnStoryPageViewModel(
    storyId: Long,
    origin: StoryOrigin,
    private val hnRepo: HackerNewsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    fun bookmarkStory(story: HnStory) {
        viewModelScope.launch(dispatcher) {
            hnRepo.saveBookmark(story)
        }
    }

    fun removeBookmark(story: HnStory) {
        viewModelScope.launch(dispatcher) {
            hnRepo.deleteBookmark(story.id)
        }
    }

    val story: StateFlow<HnStory?> = when (origin) {
        StoryOrigin.FEED -> hnRepo.getStory(storyId)
        StoryOrigin.BOOKMARKS -> hnRepo.getBookmarkedStory(storyId)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val isBookmarked: StateFlow<Boolean> = story
        .filterNotNull()
        .flatMapLatest { hnRepo.isBookmarked(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val comments: StateFlow<List<HnComment>> = hnRepo.getStoryComments(storyId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )
}