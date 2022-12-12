package dev.jpvillegas.bbcnewsrss.presentation.rss_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jpvillegas.bbcnewsrss.R
import dev.jpvillegas.bbcnewsrss.domain.model.FeedItem
import dev.jpvillegas.bbcnewsrss.presentation.ui.theme.BBCNewsRssTheme

@Composable
fun RssFeedItem(
    feedItem: FeedItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 1.0f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = feedItem.title ?: stringResource(id = R.string.no_title),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                )
                if (feedItem.description != null) {
                    Text(
                        text = feedItem.description,
                        color = Color.DarkGray,
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                if (feedItem.publicationDate != null) {
                    Text(
                        text = feedItem.publicationDate,
                        color = Color.Gray,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "go to story",
                tint = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun RssFeedItemPreview() {
    BBCNewsRssTheme {
        Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
            RssFeedItem(
                feedItem = FeedItem(
                    title = "A News story breaking right now one two three four five",
                    description = "This is the description of a story breaking",
                    link = null,
                    guid = null,
                    publicationDate = "2022-11-28 14:00:00-00"
                ),
                onClick = {}
            )
        }
    }
}