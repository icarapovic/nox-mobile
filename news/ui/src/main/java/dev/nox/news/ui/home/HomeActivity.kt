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
import dev.nox.news.ui.hn.HnStoryPage
import dev.nox.news.ui.nav.HnStoryPageNavKey
import dev.nox.news.ui.nav.HomeScreenNavKey
import dev.nox.news.ui.nav.LocalNavBackStack
import dev.nox.news.ui.theme.NoxTheme

class HomeActivity : ComponentActivity() {

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
                            entry<HnStoryPageNavKey> { HnStoryPage(it.storyId) }
                        }
                    )
                }
            }
        }
    }
}