package dev.nox.news

import dev.nox.news.ui.frontpage.NewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<NewsViewModel> { NewsViewModel(
        newsRepository = get(),
        logger = get()
    ) }
}