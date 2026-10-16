package dev.nox.news.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.nox.logging.logger.Logger
import dev.nox.news.domain.models.NewsItem
import dev.nox.news.domain.repository.NewsRepository
import dev.nox.news.domain.repository.NewsSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

private const val NEWS_COUNT = 50

class HomeViewModel(
    newsRepository: NewsRepository,
    private val logger: Logger,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), KoinComponent {

    private val _hnTopStories: MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())
    val hnTopStories = _hnTopStories.asStateFlow()
    private val _hnNewStories: MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())
    val hnNewStories = _hnNewStories.asStateFlow()

    private val _selectedTabIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedTab = _selectedTabIndex.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            newsRepository.observeNews(NewsSource.HN_TOP).collect { news ->
                _hnTopStories.value = news
            }
        }
        viewModelScope.launch(dispatcher) {
            newsRepository.observeNews(NewsSource.HN_NEW).collect { news ->
                _hnNewStories.value = news
            }
        }

        viewModelScope.launch(dispatcher) {
            newsRepository.refreshNews(NewsSource.HN_TOP, NEWS_COUNT).collect()
        }

        viewModelScope.launch(dispatcher) {
            newsRepository.refreshNews(NewsSource.HN_NEW, NEWS_COUNT).collect()
        }
    }

    fun setSelectedTab(index: Int) {
        _selectedTabIndex.value = index
    }

}