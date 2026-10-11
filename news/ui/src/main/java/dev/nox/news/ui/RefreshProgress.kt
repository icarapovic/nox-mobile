package dev.nox.news.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.nox.news.ui.frontpage.FrontPageViewModel
import org.koin.compose.viewmodel.koinViewModel

private const val FADE_OUT_MILLIS = 1000

@Composable
fun RefreshProgress(viewModel: FrontPageViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val fraction = (state.progress / 100f).coerceIn(0f, 1f)
    val done = fraction >= 1f
    val alpha by animateFloatAsState(
        targetValue = if (fraction > 0f && !done) 1f else 0f,
        animationSpec = if (done) tween(FADE_OUT_MILLIS) else snap(),
        label = "RefreshProgressAlpha"
    )

    Box(
        Modifier
            .fillMaxWidth(fraction)
            .height(2.dp)
            .graphicsLayer { this.alpha = alpha }
            .background(Color.White)
    )
}
