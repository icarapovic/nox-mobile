package dev.nox.news.ui.github

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.ui.components.ColoredDotHeader
import dev.nox.news.ui.components.SeeMoreButton
import dev.nox.news.ui.components.SourceTab
import dev.nox.news.ui.home.HomeViewModel
import dev.nox.news.ui.nav.LocalNavBackStack
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GitHubTrendingPage(
    viewModel: HomeViewModel = koinViewModel()
) {

    val repos = viewModel.githubRepos.collectAsStateWithLifecycle().value
    val nav = LocalNavBackStack.current

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        ColoredDotHeader(SourceTab.GITHUB.color, "Trending this week")
        repos.take(5).forEach { GitHubRepositoryItem(it) }
        SeeMoreButton("See more trending repositories") {

        }
    }

}