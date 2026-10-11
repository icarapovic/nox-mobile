package dev.nox.news

import dev.nox.news.ui.frontpage.FrontPageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<FrontPageViewModel> { FrontPageViewModel(
        newsRepository = get(),
        logger = get()
    ) }
}