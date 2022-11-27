package dev.jpvillegas.bbcnewsrss.presentation.rss_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.presentation.ui.theme.BBCNewsRssTheme

@Composable
fun RssFeedListScreen(
    onFeedClick: ((RssFeed) -> Unit)? = null,
    viewModel: RssFeedListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    RssFeedListContent(
        feeds = state.feeds,
        onFeedClick = onFeedClick
    )
}

@Composable
fun RssFeedListContent(
    feeds: List<RssFeed>,
    onFeedClick: ((RssFeed) -> Unit)? = null
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(16.dp)
            ) {
                Text(
                    text = "BBC News RSS Feeds",
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {


            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(feeds) { feed ->
                    RssFeedListItem(feed = feed, onClick = onFeedClick)
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
        }
    }

}

@Preview
@Composable
fun RssFeedListScreenPreview() {
    BBCNewsRssTheme {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            RssFeedListContent(
                feeds = (1..10).map {
                    RssFeed(
                        title = "BBC News $it",
                        description = "A feed description",
                        link = null,
                        thumbnailUrl = null,
                        url = "",
                        items = emptyList()
                    )
                }
            )
        }
    }
}

