package dev.nox.news.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nox.news.domain.models.Source
import dev.nox.news.ui.components.BookmarkIcon
import dev.nox.news.ui.components.ColoredDotHeader
import dev.nox.news.ui.components.SearchIcon
import dev.nox.news.ui.components.Page
import dev.nox.news.ui.theme.PlexMono
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToBookmarks: () -> Unit = { },
    onNavigateToSearch: () -> Unit = { }
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = {
                    Text(
                        modifier = Modifier.padding(start = 12.dp),
                        text = DateTimeFormatter.ofPattern("EEEE, dd. MMMM").format(LocalDateTime.now()),
                        color = Color.Gray,
                        fontFamily = PlexMono,
                        fontSize = 16.sp
                    )
                },
                actions = {
                    BookmarkIcon { onNavigateToBookmarks() }
                    SearchIcon { onNavigateToSearch() }
                }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                HomeActivityViewPager()
            }
        }
    )
}


@Composable
fun HomeActivityViewPager(
    modifier: Modifier = Modifier,
) {
    val sourceTabs = Source.entries
    val pagerState = rememberPagerState { sourceTabs.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        PrimaryScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 8.dp,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    Modifier.tabIndicatorOffset(pagerState.currentPage, matchContentSize = true),
                    width = Dp.Unspecified,
                    color = Color(sourceTabs[pagerState.currentPage].tabColor)
                )
            }
        ) {
            sourceTabs.forEachIndexed { index, source ->
                Tab(
                    selected = index == pagerState.currentPage,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            text = source.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page -> Page(sourceTabs[page]) }
    }
}
