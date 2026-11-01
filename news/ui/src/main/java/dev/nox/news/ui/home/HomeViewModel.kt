package dev.nox.news.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.nox.logging.logger.Logger
import dev.nox.news.domain.models.Source
import dev.nox.news.domain.models.hackernews.HnStory
import dev.nox.news.domain.repository.HackerNewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.time.Duration.Companion.minutes
import kotlin.time.TimeMark
import kotlin.time.TimeSource

private val RESUME_REFRESH_INTERVAL = 10.minutes

class HomeViewModel(
    private val hnRepo: HackerNewsRepository,
    private val logger: Logger,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val timeSource: TimeSource = TimeSource.Monotonic
) : ViewModel(), KoinComponent {

    private var lastRefresh: TimeMark? = null

    private val _hnTopStories: MutableStateFlow<List<HnStory>> = MutableStateFlow(emptyList())
    val hnTopStories = _hnTopStories.asStateFlow()
    private val _hnNewStories: MutableStateFlow<List<HnStory>> = MutableStateFlow(emptyList())
    val hnNewStories = _hnNewStories.asStateFlow()

    private val _hnBestStories: MutableStateFlow<List<HnStory>> = MutableStateFlow(emptyList())
    val hnBestStories = _hnBestStories.asStateFlow()

    private val _hnAskStories: MutableStateFlow<List<HnStory>> = MutableStateFlow(emptyList())
    val hnAskStories = _hnAskStories.asStateFlow()

    private val _hnShowStories: MutableStateFlow<List<HnStory>> = MutableStateFlow(emptyList())
    val hnShowStories = _hnShowStories.asStateFlow()

    private val _hnJobsStories: MutableStateFlow<List<HnStory>> = MutableStateFlow(emptyList())
    val hnJobsStories = _hnJobsStories.asStateFlow()
    val categoryStoryCount = 50

    init {
        viewModelScope.launch(dispatcher) { hnRepo.observeTopStories().collect { news ->
            _hnTopStories.value = news
        }}

        viewModelScope.launch(dispatcher) { hnRepo.observeNewStories().collect { news ->
            _hnNewStories.value = news
        } }

        viewModelScope.launch(dispatcher) { hnRepo.observeBestStories().collect { news ->
            _hnBestStories.value = news
        } }

        viewModelScope.launch(dispatcher) { hnRepo.observeAskStories().collect { news ->
            _hnAskStories.value = news
        } }

        viewModelScope.launch(dispatcher) { hnRepo.observeShowStories().collect { news ->
            _hnShowStories.value = news
        } }

        viewModelScope.launch(dispatcher) { hnRepo.observeJobsStories().collect { news ->
            _hnJobsStories.value = news
        } }

        fetch(Source.HN_TOP)
        fetch(Source.HN_NEW)
        fetch(Source.HN_BEST)
        fetch(Source.HN_ASK)
        fetch(Source.HN_SHOW)
        fetch(Source.HN_JOBS)
    }

    private fun fetch(source: Source) {
        viewModelScope.launch(dispatcher) {
            hnRepo.refreshData(source).collect()
        }
    }

}