package dev.nox.news.ui.hn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.nox.news.domain.repository.HackerNewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HnBookmarksViewModel(
    private val hnRepo: HackerNewsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val bookmarks = hnRepo.getBookmarks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun deleteBookmark(storyId: Long) {
        viewModelScope.launch(dispatcher) {
            hnRepo.deleteBookmark(storyId)
        }
    }
}