package dev.nox.news.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.nox.news.ui.hn.HackerNewsMorePage
import dev.nox.news.ui.nav.CategoryMorePageNavKey
import dev.nox.news.ui.nav.HomeScreenNavKey
import dev.nox.news.ui.nav.LocalNavBackStack
import dev.nox.news.ui.theme.NoxTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : ComponentActivity() {

    val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoxTheme {
                val backStack = rememberNavBackStack(HomeScreenNavKey)
                CompositionLocalProvider(LocalNavBackStack provides backStack) {
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        entryProvider = entryProvider {
                            entry<HomeScreenNavKey> { HomeScreen() }
                            entry<CategoryMorePageNavKey> { HackerNewsMorePage(it.newsSource) }
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshNewsOnResume()
    }
}