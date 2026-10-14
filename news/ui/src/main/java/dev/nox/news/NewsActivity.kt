package dev.nox.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import dev.nox.news.ui.RefreshProgress
import dev.nox.news.ui.components.BookmarkIcon
import dev.nox.news.ui.components.NavigateBackIcon
import dev.nox.news.ui.components.SearchIcon
import dev.nox.news.ui.components.SourceTabs
import dev.nox.news.ui.frontpage.NewsViewModel
import dev.nox.news.ui.frontpage.HackerNews
import dev.nox.news.ui.theme.NoxTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewsActivity : ComponentActivity() {

    val viewModel: NewsViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoxTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            modifier = Modifier
                                .background(Color.Black)
                                .fillMaxWidth(),
                            title = {
                                Text(
                                    text = DateTimeFormatter.ofPattern("EEEE, MMMM dd").format(LocalDateTime.now()),
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            },
                            navigationIcon = {
                                NavigateBackIcon(true) {  }
                            },
                            actions = {
                                BookmarkIcon {  }
                                SearchIcon {  }
                            }
                        )
                    },
                    content = { paddingValues ->
                        Column(modifier = Modifier.padding(paddingValues)) {
                            SourceTabs(viewModel)
                            Box {
                                HackerNews()
                                RefreshProgress()
                            }
                        }
                    }
                )
            }
        }
    }
}