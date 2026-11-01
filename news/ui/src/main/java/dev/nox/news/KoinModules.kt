package dev.nox.news

import dev.nox.news.ui.hn.HnBookmarksViewModel
import dev.nox.news.ui.hn.HnStoryPageViewModel
import dev.nox.news.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<HomeViewModel> { HomeViewModel(
        hnRepo = get(),
        logger = get()
    ) }

    viewModel<HnStoryPageViewModel> { params ->
        HnStoryPageViewModel(storyId = params.get(), hnRepo = get())
    }

    viewModel<HnBookmarksViewModel> { HnBookmarksViewModel(get()) }
}