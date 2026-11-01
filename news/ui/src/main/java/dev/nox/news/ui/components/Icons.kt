package dev.nox.news.ui.components

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChecklistRtl
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import dev.nox.news.ui.nav.LocalNavBackStack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkIcon(
    bookmarked: Boolean = false,
    onClick: () -> Unit
) {
    TooltipBox(
        positionProvider =
            TooltipDefaults.rememberTooltipPositionProvider(
                TooltipAnchorPosition.Above
            ),
        tooltip = { PlainTooltip { Text("Bookmark story") } },
        state = rememberTooltipState(),
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if(bookmarked) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = "Bookmark story",
                tint = Color.White
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
                imageVector = Icons.Outlined.Search,
                contentDescription = "Add to favorites",
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsIcon(onClick: () -> Unit) {
    TooltipBox(
        positionProvider =
            TooltipDefaults.rememberTooltipPositionProvider(
                TooltipAnchorPosition.Above
            ),
        tooltip = { PlainTooltip { Text("Settings") } },
        state = rememberTooltipState(),
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                tint = Color.White
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
                imageVector = Icons.Outlined.ArrowBackIosNew,
                contentDescription = "Menu",
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareIcon(url: String) {
    val context = LocalContext.current
    TooltipBox(
        positionProvider =
            TooltipDefaults.rememberTooltipPositionProvider(
                TooltipAnchorPosition.Above
            ),
        tooltip = { PlainTooltip { Text("Share") } },
        state = rememberTooltipState(),
    ) {
        IconButton(onClick = {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, url)
            }
            context.startActivity(Intent.createChooser(sendIntent, null))
        }) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "Share",
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectMultipleIcon(onClick: () -> Unit) {
    TooltipBox(
        positionProvider =
            TooltipDefaults.rememberTooltipPositionProvider(
                TooltipAnchorPosition.Above
            ),
        tooltip = { PlainTooltip { Text("Select multiple") } },
        state = rememberTooltipState(),
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Outlined.ChecklistRtl,
                contentDescription = "Select multiple",
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteIcon(iconColor: Color = Color.White, onClick: () -> Unit) {
    TooltipBox(
        positionProvider =
            TooltipDefaults.rememberTooltipPositionProvider(
                TooltipAnchorPosition.Above
            ),
        tooltip = { PlainTooltip { Text("Delete") } },
        state = rememberTooltipState(),
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete",
                tint = iconColor
            )
        }
    }
}

@Composable
fun StarIcon() {
   Icon(
       imageVector = Icons.Outlined.Star,
       contentDescription = "Star count",
       tint = Color.White
   )
}