package dev.nox.news.ui.components

import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.ui.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SourceTabs(
    viewModel: HomeViewModel
) {
    val selectedTabIndex = viewModel.selectedTab.collectAsStateWithLifecycle()
    val sourceTabs = SourceTab.entries

    PrimaryScrollableTabRow(
        selectedTabIndex = selectedTabIndex.value,
        edgePadding = 16.dp,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                Modifier.tabIndicatorOffset(selectedTabIndex.value, matchContentSize = true),
                width = Dp.Unspecified,
                color = sourceTabs[selectedTabIndex.value].color
            )
        }
    ) {
        sourceTabs.forEachIndexed { index, source ->
            Tab(
                selected = index == selectedTabIndex.value,
                onClick = { viewModel.setSelectedTab(index) },
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
}