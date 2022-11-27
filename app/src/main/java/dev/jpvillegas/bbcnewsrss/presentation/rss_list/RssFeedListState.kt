package dev.jpvillegas.bbcnewsrss.presentation.rss_list

import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed

data class RssFeedListState(
    val isLoading: Boolean = false,
    val feeds: List<RssFeed> = emptyList(),
    val error: RepositoryError = RepositoryError.None
)