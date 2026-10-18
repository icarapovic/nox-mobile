package dev.nox.news.ui.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.TransitEnterexit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import dev.nox.news.ui.theme.PlexSans

@Composable
fun SeeMoreButton(
    title: String,
    onSeeMoreClicked: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        onClick = onSeeMoreClicked,
        content = {
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontFamily = PlexSans,
                    fontSize = 14.sp
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(16.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    tint = Color.White,
                    contentDescription = "Forward icon"
                )
            }
        }
    )
}

@Composable
fun ButtonOpenInBrowser(url: String) {
    val context = LocalContext.current

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            ),
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        },
        content = {
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Open in browser",
                    color = Color.Black,
                    fontFamily = PlexSans,
                    fontSize = 14.sp
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(16.dp)
                        .rotate(180f),
                    imageVector = Icons.Default.TransitEnterexit,
                    contentDescription = "Forward icon"
                )
            }
        }
    )
}