package dev.jpvillegas.bbcnewsrss.presentation.rss_feed

import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError

data class RssFeedState(
    val isLoading: Boolean = false,
    val feed: RssFeed? = null,
    val error: RepositoryError = RepositoryError.None
)
