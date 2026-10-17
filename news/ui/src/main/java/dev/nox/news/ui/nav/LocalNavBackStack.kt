package dev.nox.news.ui.nav

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

val LocalNavBackStack = staticCompositionLocalOf<NavBackStack<NavKey>> {
    error("LocalNavBackStack not provided")
}
