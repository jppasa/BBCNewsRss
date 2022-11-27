package dev.jpvillegas.bbcnewsrss.domain.repository

import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed

interface RssFeedRepository {
    suspend fun getRssFeeds() : List<RssFeed>
}