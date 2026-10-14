package dev.nox.news.ui.frontpage

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
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

private const val NEWS_COUNT = 30

class NewsViewModel(
    newsRepository: NewsRepository,
    private val logger: Logger,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), KoinComponent {

    private val _news: MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())
    val newsState = _news.asStateFlow()

    private val _selectedTabIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedTab = _selectedTabIndex.asStateFlow()

    private val _progress: MutableStateFlow<Float> = MutableStateFlow(0f)
    val progressState = _progress.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            newsRepository.observeNews(NewsSource.HN_TOP).collect { news ->
                _news.value = news
            }
        }

        viewModelScope.launch(dispatcher) {
            logger.info { "Starting refresh..." }
            val time = System.currentTimeMillis()
            newsRepository.refreshNews(NewsSource.HN_TOP, NEWS_COUNT).collect { percent ->
                if(percent >= 100f) {
                    logger.info { "Refresh done, took ${System.currentTimeMillis() - time}ms" }
                }
                _progress.value = percent
            }
        }
    }

    fun setSelectedTab(index: Int) {
        _selectedTabIndex.value = index
    }

}