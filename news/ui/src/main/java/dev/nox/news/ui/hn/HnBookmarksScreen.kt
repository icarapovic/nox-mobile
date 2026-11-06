package dev.nox.news.ui.hn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.domain.models.hackernews.HnStoryBookmark
import dev.nox.news.ui.components.DeleteIcon
import dev.nox.news.ui.components.HorizontalDivider
import dev.nox.news.ui.components.NavigateBackIcon
import dev.nox.news.ui.components.SelectMultipleIcon
import dev.nox.news.ui.components.TextLarge
import dev.nox.news.ui.components.TextNormal
import dev.nox.news.ui.components.TextSmall
import dev.nox.news.ui.formatTimestampBookmark
import dev.nox.news.ui.nav.HnStoryPageNavKey
import dev.nox.news.ui.nav.LocalNavBackStack
import dev.nox.news.ui.nav.StoryOrigin
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HnBookmarksScreen(
    viewModel: HnBookmarksViewModel = koinViewModel()
) {
    val isSelectedMultipleActive = remember { mutableStateOf(false) }
    val listToDelete = remember { mutableStateListOf<HnStoryBookmark>() }

    Column(
        modifier = Modifier
            .padding(WindowInsets.systemBars.asPaddingValues())
            .fillMaxHeight()
            .background(Color.Black)
    ) {
        HnBookmarkHeader(listToDelete, isSelectedMultipleActive, viewModel)
        HorizontalDivider()
        HnBookmarkList(listToDelete, isSelectedMultipleActive, viewModel)
    }
}

@Composable
fun HnBookmarkHeader(
    listToDelete: MutableList<HnStoryBookmark>,
    isSelectedMultipleActive: MutableState<Boolean>,
    viewModel: HnBookmarksViewModel
) {
    val bookmarks = viewModel.bookmarks.collectAsStateWithLifecycle().value
    var showRemoveAllDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 8.dp),
    ){
        Row {
            NavigateBackIcon()
            Spacer(Modifier.weight(1f))
            if(isSelectedMultipleActive.value && listToDelete.isNotEmpty()) {
                DeleteIcon(Color.Red) { showRemoveAllDialog = true }
            }
            SelectMultipleIcon {
                isSelectedMultipleActive.value = !isSelectedMultipleActive.value
                if(!isSelectedMultipleActive.value) {
                    listToDelete.clear()
                }
            }
        }

        Row (
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically)
        {
            if(!isSelectedMultipleActive.value) {
                TextLarge("Saved")
            } else {
                TextLarge("Delete multiple", textColor = Color.Red)
            }

            Spacer(Modifier.weight(1f))
            TextSmall("${bookmarks.size} stories")
        }

    }

    if (showRemoveAllDialog) {
        RemoveBookmarkDialog(
            count = listToDelete.size,
            onConfirm = {
                showRemoveAllDialog = false
                listToDelete.forEach { it.id?.let { storyId ->
                    viewModel.deleteBookmark(storyId)
                }}
                listToDelete.clear()
                isSelectedMultipleActive.value = false
            },
            onDismiss = { showRemoveAllDialog = false }
        )
    }

}

@Composable
fun HnBookmarkList(
    listToDelete: MutableList<HnStoryBookmark>,
    isSelectedMultipleActive: MutableState<Boolean>,
    viewModel: HnBookmarksViewModel
) {
    val bookmarks = viewModel.bookmarks.collectAsStateWithLifecycle().value

    LazyColumn {
        items(bookmarks.size) { index ->
            HnBookmarkItem(listToDelete, isSelectedMultipleActive, bookmarks[index], viewModel)
        }
    }
}

@Composable
fun HnBookmarkItem(
    listToDelete: MutableList<HnStoryBookmark>,
    isSelectedMultipleActive: MutableState<Boolean>,
    bookmark: HnStoryBookmark,
    viewModel: HnBookmarksViewModel
) {
    var showRemoveDialog by remember { mutableStateOf(false) }
    val backStack = LocalNavBackStack.current
    val isChecked = bookmark in listToDelete
    val toggleSelection = {
        if (isChecked) listToDelete.remove(bookmark) else listToDelete.add(bookmark)
    }

    Column(
        modifier = Modifier.clickable {
            if (isSelectedMultipleActive.value) {
                toggleSelection()
            } else {
                bookmark.id?.let { storyId ->
                    backStack.add(HnStoryPageNavKey(storyId, StoryOrigin.BOOKMARKS))
                }
            }
        }
    ) {
        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                TextNormal(bookmark.title)
                Row {
                    TextSmall(bookmark.bookmarkedAt.formatTimestampBookmark())
                }
            }

            if(isSelectedMultipleActive.value) {
                Checkbox(isChecked, onCheckedChange = { toggleSelection() })
            } else {
                DeleteIcon(Color.White) { showRemoveDialog = true }
            }
        }
        HorizontalDivider()
    }

    if (showRemoveDialog) {
        RemoveBookmarkDialog(
            onConfirm = {
                showRemoveDialog = false
                bookmark.id?.let { storyId ->
                    viewModel.deleteBookmark(storyId)
                }
            },
            onDismiss = { showRemoveDialog = false }
        )
    }
}

@Composable
private fun RemoveBookmarkDialog(
    count: Int = 1,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            if(count == 1) {
                TextNormal("Remove this bookmark?")
            } else {
                TextNormal("Remove $count bookmarks?")
            }

        },
        confirmButton = {
            TextButton(onClick = onConfirm) { TextNormal("Yes") }
        },
        containerColor = Color(0xFF1C1C1C),
        dismissButton = {
            TextButton(onClick = onDismiss) { TextNormal("No") }
        }
    )
}