package dev.nox.news.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nox.news.ui.components.SourceTab
import dev.nox.news.ui.hn.HackerNewsPage
import kotlinx.coroutines.launch

@Composable
fun HomeActivityViewPager(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val sourceTabs = SourceTab.entries
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
                    color = sourceTabs[pagerState.currentPage].color
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
        ) { page ->
            when (sourceTabs[page]) {
                SourceTab.HACKER_NEWS -> HackerNewsPage(viewModel)
                SourceTab.GITHUB,
                SourceTab.GITLAB,
                SourceTab.CODEBERG,
                SourceTab.ARS_TECHNICA -> PlaceholderPage()
            }
        }
    }
}

@Composable
private fun PlaceholderPage() {
    Box(modifier = Modifier.fillMaxSize())
}
