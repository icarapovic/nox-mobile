package dev.nox.news.ui.components

import androidx.compose.foundation.layout.visible
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.nox.news.ui.nav.LocalNavBackStack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkIcon(onClick: () -> Unit) {
    TooltipBox(
        positionProvider =
            TooltipDefaults.rememberTooltipPositionProvider(
                TooltipAnchorPosition.Above
            ),
        tooltip = { PlainTooltip { Text("Add to favorites") } },
        state = rememberTooltipState(),
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Filled.BookmarkBorder,
                contentDescription = "Add to favorites",
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchIcon(onClick: () -> Unit) {
    TooltipBox(
        positionProvider =
            TooltipDefaults.rememberTooltipPositionProvider(
                TooltipAnchorPosition.Above
            ),
        tooltip = { PlainTooltip { Text("Add to favorites") } },
        state = rememberTooltipState(),
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Add to favorites",
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigateBackIcon() {
    val backStack = LocalNavBackStack.current
    TooltipBox(
        positionProvider =
            TooltipDefaults.rememberTooltipPositionProvider(
                TooltipAnchorPosition.Above
            ),
        tooltip = { PlainTooltip { Text("Menu") } },
        state = rememberTooltipState(),
    ) {
        IconButton(
            onClick = { backStack.removeLastOrNull() }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Menu",
                tint = Color.White
            )
        }
    }
}