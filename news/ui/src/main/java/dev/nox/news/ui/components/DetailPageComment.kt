package dev.nox.news.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nox.news.domain.models.hackernews.HnComment
import dev.nox.news.ui.formatAge
import dev.nox.news.ui.theme.PlexMono
import dev.nox.news.ui.theme.PlexSans
import java.time.Duration
import java.time.Instant

/**
 * Comments header and comment items, added to the caller's lazy list
 * so they scroll together with the content above them
 * */
fun LazyListScope.detailPageComments(
    storyOp: String?,
    comments: List<HnComment>
) {
    item(key = "comments_header") {
        Column {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(0.25.dp)
                    .background(Color.DarkGray)
                    .fillMaxWidth()
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = "${comments.size} comments",
                fontFamily = PlexSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White
            )

            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .height(0.25.dp)
                    .background(Color.DarkGray)
                    .fillMaxWidth()
            )
        }
    }

    items(comments.size, key = { index -> comments[index].id }) { index ->
        HackerNewsComment(storyOp, comments[index])
    }

}

@Composable
fun HackerNewsComment(
    storyOp: String?,
    hnComment: HnComment
) {
    val age = Duration.between(Instant.ofEpochSecond(hnComment.time), Instant.now()).formatAge()

    hnComment.text?.let { comment ->
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            repeat(hnComment.depth) {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxHeight()
                        .background(Color.DarkGray)
                        .width(2.dp)
                )
            }

            Column {
                Row {
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 12.dp,
                            bottom = 8.dp
                        ),
                        color = if(storyOp == hnComment.by && storyOp != null) Color(0xFFFF6A00) else Color.White ,
                        fontFamily = PlexSans,
                        fontWeight = FontWeight.Bold,
                        text = hnComment.by ?: "Unknown"
                    )

                    if(storyOp != null && storyOp == hnComment.by) {
                        Text(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                top = 12.dp,
                                bottom = 8.dp
                            ).background(Color.DarkGray, shape = RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp),
                            color = Color.White,
                            fontFamily = PlexSans,
                            fontWeight = FontWeight.Bold,
                            text = "OP"
                        )
                    }

                    Text(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 12.dp,
                            bottom = 8.dp
                        ),
                        color = Color.Gray,
                        fontFamily = PlexMono,
                        text = age
                    )
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 8.dp),
                    text = AnnotatedString.fromHtml(comment),
                    color = Color(0xFFD2D1D1)
                )

                Box(
                    modifier = Modifier
                        .height(0.25.dp)
                        .background(Color.DarkGray)
                        .fillMaxWidth()
                )
            }
        }

    }
}