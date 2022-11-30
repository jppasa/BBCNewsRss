package dev.jpvillegas.bbcnewsrss.presentation.rss_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jpvillegas.bbcnewsrss.R
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.presentation.ui.theme.BBCNewsRssTheme

@Composable
fun RssFeedListScreen(
    onFeedClick: ((RssFeed) -> Unit)? = null,
    viewModel: RssFeedListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    RssFeedListContent(
        feeds = state.feeds,
        isLoading = state.isLoading,
        error = state.error,
        onFeedClick = onFeedClick
    )
}

@Composable
fun RssFeedListContent(
    feeds: List<RssFeed>,
    isLoading: Boolean,
    onFeedClick: ((RssFeed) -> Unit)? = null,
    error: RepositoryError
) {
    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bbc_logo_no_background),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier.height(40.dp)
                )
                Text(
                    text = stringResource(id = R.string.news_rss),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AnimatedVisibility(
                visible = isLoading,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
            ) {
                CircularProgressIndicator()
            }

            AnimatedVisibility(
                visible = error != RepositoryError.None,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
            ) {
                val errorStr = when (error) {
                    RepositoryError.Network -> stringResource(id = R.string.network_error)
                    RepositoryError.Unknown -> stringResource(id = R.string.unknown_error)
                    RepositoryError.None -> ""
                }

                Text(
                    text = errorStr,
                    style = MaterialTheme.typography.body2
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }

                items(feeds) { feed ->
                    AnimatedVisibility(
                        visible = !isLoading,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                    ) {
                        RssFeedListItem(feed = feed, onClick = onFeedClick)
                    }
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
                        id = it,
                        title = "BBC News $it",
                        description = "A feed description",
                        link = null,
                        thumbnailUrl = null,
                        url = "",
                        items = emptyList()
                    )
                },
                isLoading = false,
                error = RepositoryError.None
            )
        }
    }
}

