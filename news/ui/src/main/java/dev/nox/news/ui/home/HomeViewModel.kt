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
import kotlin.time.Duration.Companion.minutes
import kotlin.time.TimeMark
import kotlin.time.TimeSource

private const val NEWS_COUNT = 50
private val RESUME_REFRESH_INTERVAL = 10.minutes

class HomeViewModel(
    private val newsRepository: NewsRepository,
    private val logger: Logger,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val timeSource: TimeSource = TimeSource.Monotonic
) : ViewModel(), KoinComponent {

    private var lastRefresh: TimeMark? = null

    private val _hnTopStories: MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())
    val hnTopStories = _hnTopStories.asStateFlow()
    private val _hnNewStories: MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())
    val hnNewStories = _hnNewStories.asStateFlow()

    private val _hnBestStories: MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())
    val hnBestStories = _hnBestStories.asStateFlow()

    private val _hnAskStories: MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())
    val hnAskStories = _hnAskStories.asStateFlow()

    private val _hnShowStories: MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())
    val hnShowStories = _hnShowStories.asStateFlow()

    private val _hnJobsStories: MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())
    val hnJobsStories = _hnJobsStories.asStateFlow()

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
            newsRepository.observeNews(NewsSource.HN_BEST).collect { news ->
                _hnBestStories.value = news
            }
        }

        viewModelScope.launch(dispatcher) {
            newsRepository.observeNews(NewsSource.HN_ASK).collect { news ->
                _hnAskStories.value = news
            }
        }

        viewModelScope.launch(dispatcher) {
            newsRepository.observeNews(NewsSource.HN_SHOW).collect { news ->
                _hnShowStories.value = news
            }
        }

        viewModelScope.launch(dispatcher) {
            newsRepository.observeNews(NewsSource.HN_JOB).collect { news ->
                _hnJobsStories.value = news
            }
        }

    }

    /**
     * Refreshes news unless the last refresh started less than
     * [RESUME_REFRESH_INTERVAL] ago
     * */
    fun refreshNewsOnResume() {
        val elapsed = lastRefresh?.elapsedNow()
        if (elapsed != null && elapsed < RESUME_REFRESH_INTERVAL) return
        refreshNews()
    }

    fun refreshNews() {
        lastRefresh = timeSource.markNow()
        NewsSource.entries.forEach { source ->
            viewModelScope.launch(dispatcher) {
                newsRepository.refreshNews(source, NEWS_COUNT).collect()
            }
        }
    }

}