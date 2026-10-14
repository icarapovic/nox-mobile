package dev.nox.news

import dev.nox.news.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<HomeViewModel> { HomeViewModel(
        newsRepository = get(),
        logger = get()
    ) }
}