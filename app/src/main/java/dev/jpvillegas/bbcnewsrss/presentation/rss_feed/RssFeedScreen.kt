package dev.jpvillegas.bbcnewsrss.presentation.rss_feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jpvillegas.bbcnewsrss.R
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.presentation.ui.theme.BBCNewsRssTheme

@Composable
fun RssFeedScreen(
    viewModel: RssFeedViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    RssFeedContent(feed = state.feed)
}

@Composable
fun RssFeedContent(feed: RssFeed?) {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            // go back
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back),
                            //tint = Color.DarkGray
                        )
                    }

                    Text(
                        text = feed?.title ?: stringResource(id = R.string.empty_title),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    )
                }
            }
        }
    ) { padding ->
        if (feed == null) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.source_not_found),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(feed.items) {
                    RssFeedItem(
                        feedItem = it
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
        }
    }
}

@Preview
@Composable
fun RssFeedScreenPreview() {
    BBCNewsRssTheme {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            RssFeedContent(
                feed = RssFeed(
                    id = 0,
                    title = "An RSS Feed",
                    description = "A description of the feed",
                    link = null,
                    thumbnailUrl = null,
                    url = "test.com",
                    items = (1..10).map {
                        dev.jpvillegas.bbcnewsrss.domain.model.RssFeedItem(
                            title = "Feed item $it",
                            description = "This is the description for feed item $it",
                            link = null,
                            guid = null,
                            publicationDate = "2022-11-28 14:00:00-00"
                        )
                    }
                ),
            )
        }
    }
}
