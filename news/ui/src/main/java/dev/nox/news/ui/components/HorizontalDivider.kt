package dev.nox.news.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDivider(paddingValues: PaddingValues = PaddingValues(vertical = 8.dp)) {
    Spacer(modifier = Modifier.padding(paddingValues).background(Color.DarkGray).fillMaxWidth().height(0.25.dp))
}