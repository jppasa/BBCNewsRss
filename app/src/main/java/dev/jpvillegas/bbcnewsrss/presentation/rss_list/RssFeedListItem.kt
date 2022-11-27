package dev.jpvillegas.bbcnewsrss.presentation.rss_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.jpvillegas.bbcnewsrss.R
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.presentation.ui.theme.BBCNewsRssTheme

@Composable
fun RssFeedListItem(
    feed: RssFeed,
    onClick: ((RssFeed) -> Unit)? = null
) {
    val title = feed.title ?: stringResource(id = R.string.empty_title)
    val description = feed.description
    val imageUrl = feed.thumbnailUrl

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick?.invoke(feed) }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier.size(64.dp)
            ) {
                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = stringResource(id = R.string.item_image, title),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.bbc_logo_big),
                        contentDescription = stringResource(id = R.string.item_image, title),
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(8.dp))

                    )
                }
            }

            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    style = TextStyle.Default.copy(
                        fontWeight = FontWeight.Medium,
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                if (description != null) {
                    Text(
                        text = description,
                        style = TextStyle(
                            color = MaterialTheme.colors.onSurface,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RssFeedListItemPreview() {
    BBCNewsRssTheme {
        RssFeedListItem(
            feed = RssFeed(
                title = "BBC News World Wide",
                description = "An Rss feed like no other",
                link = null,
                thumbnailUrl = null,
                url = "",
                items = emptyList()
            )
        )
    }
}